package com.github.nathandelane.timetracker.data;

import com.github.nathandelane.timetracker.model.*;
import org.slf4j.*;

import java.io.*;
import java.nio.charset.*;
import java.sql.*;
import java.util.*;

/**
 * WorkTaskDao data access object. Only a single instance of this should exist. This is a managed class.
 */
public final class WorkTaskDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkTaskDao.class);

  private final DataSource dataSource;

  public WorkTaskDao(final DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public List<WorkTask> getAllWorkTasks() {
    final String query = loadResourceAsString("sql/get_all_worktasks.sql");

    return queryWorkTasks(query);
  }

  public WorkTask getWorkTaskById(final long workTaskId) {
    String query = loadResourceAsString("sql/get_worktask_by_id.sql");
    query = query.replace(":work_task_id", Long.toString(workTaskId));

    final List<WorkTask> workTasks = queryWorkTasks(query);

    if (workTasks == null || workTasks.isEmpty()) return null;

    return workTasks.get(0);
  }

  public int createWorkTask(
    final WorkTask workTask
  ) {
    String query = loadResourceAsString("sql/create_worktask.sql");
    query = query.replace(":start_date_and_time", Long.toString(workTask.startDateAndTime));
    query = query.replace(":description", workTask.description);
    query = query.replace(":who_caused_it", workTask.whoCausedIt);
    query = query.replace(":planned", (workTask.wasPlanned ? "Y" : "N"));
    query = query.replace(":action_item", (workTask.actionItem == null ? "null" : String.format("'%s'", workTask.actionItem)));
    query = query.replace(":category_of_work", workTask.categoryOfWork);
    query = query.replace(":project", (workTask.project == null ? "null" : String.format("'%s'", workTask.project)));

    final int recordsUpdated = updateWorkTasks(query);

    return recordsUpdated;
  }

  public List<WorkTask> queryWorkTasks(final String sqlSelectQuery) {
    final List<WorkTask> workTasks = new ArrayList<>();

    try (final Connection connection = dataSource.getConnection()) {
      final PreparedStatement statement = connection.prepareStatement(sqlSelectQuery);
      final ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        final WorkTask workTask = createWorkTaskFromResults(resultSet);

        workTasks.add(workTask);
      }
    } catch (final SQLException e) {
      LOGGER.error("Exception occurred querying database.", e);
    }

    return workTasks;
  }

  public int updateWorkTasks(final String sqlUpdateQuery) {
    int recordsUpdatedOrInserted = 0;

    try (final Connection connection = dataSource.getConnection()) {
      final PreparedStatement statement = connection.prepareStatement(sqlUpdateQuery);

      recordsUpdatedOrInserted = statement.executeUpdate();
    } catch (final SQLException e) {
      LOGGER.error("Exception occurred updating database.", e);
    }

    return recordsUpdatedOrInserted;
  }

  public final String loadResourceAsString(final String resourceFileName) throws RuntimeException {
    final StringBuilder sb = new StringBuilder();

    try (
      final InputStream is = WorkTaskDao.class.getClassLoader().getResourceAsStream(resourceFileName);
      final InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
      final BufferedReader reader = new BufferedReader(streamReader)
    ) {
      String line;

      while ((line = reader.readLine()) != null) {
        sb.append(line).append(System.lineSeparator());
      }
    } catch (final IOException e) {
      LOGGER.error("Could not load resource file optionSkuMap.csv.", e);

      throw new RuntimeException(e);
    }

    return sb.toString();
  }

  public WorkTask createWorkTaskFromResults(final ResultSet resultSet) throws SQLException {
    final int id = resultSet.getInt("id");
    final long epochSeconds = resultSet.getLong("start_date_and_time");
    final String description = resultSet.getString("description");
    final String whoCausedIt = resultSet.getString("who_caused_it");
    final boolean wasPlanned = resultSet.getString("planned").equalsIgnoreCase("Y");
    final String actionItem = resultSet.getString("action_item");
    final String categoryOfWork = resultSet.getString("category_of_work");
    final String project = resultSet.getString("project");

    final WorkTask workTask = new WorkTask(
      id,
      epochSeconds,
      description,
      whoCausedIt,
      wasPlanned,
      actionItem,
      categoryOfWork,
      project
    );

    return workTask;
  }

}
