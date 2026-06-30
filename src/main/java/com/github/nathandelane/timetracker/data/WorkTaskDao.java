package com.github.nathandelane.timetracker.data;

import com.github.nathandelane.timetracker.model.WorkTask;
import com.github.nathandelane.timetracker.util.SqlScripts;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class WorkTaskDao {

  private WorkTaskDao() { }

  public static void saveWorkTasks(final List<WorkTask> workTaskList) {
    for (final WorkTask nextWorkTask : workTaskList) {
      saveWorkTask(nextWorkTask);
    }
  }

  public static int saveWorkTask(final WorkTask workTask) {
    int numRowsAffected = -1;

    if (workTask.id == null) {
      final String sql = SqlScripts.fromResources("sql/insert_new_work_task.sql");

      try (
        final Connection conn = DriverManager.getConnection(DbProvider.getJdbc());
        final PreparedStatement statement = conn.prepareStatement(sql)
      ) {
        final String strStartDateTime = workTask.startDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        statement.setString(1, strStartDateTime);
        statement.setString(2, workTask.description);
        statement.setString(3, workTask.requestor);
        statement.setString(4, workTask.isPlanned ? "Y" : "N");
        statement.setString(5, workTask.project);
        statement.setString(6, workTask.category);

        numRowsAffected = statement.executeUpdate();
      } catch (final SQLException e) {
        e.printStackTrace();
      }
    }
    else {
      final String sql = SqlScripts.fromResources("sql/update_existing_work_task.sql");

      try (
        final Connection conn = DriverManager.getConnection(DbProvider.getJdbc());
        final PreparedStatement statement = conn.prepareStatement(sql)
      ) {
        final String strStartDateTime = workTask.startDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        final String strEndDateTime = workTask.endDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        statement.setString(1, strStartDateTime);
        statement.setString(2, workTask.description);
        statement.setString(3, workTask.requestor);
        statement.setString(4, workTask.isPlanned ? "Y" : "N");
        statement.setString(5, workTask.project);
        statement.setString(6, workTask.category);
        statement.setString(7, strEndDateTime);
        statement.setLong(8, workTask.id);

        numRowsAffected = statement.executeUpdate();
      } catch (final SQLException e) {
        e.printStackTrace();
      }
    }

    return numRowsAffected;
  }

  public static WorkTask findWorkTask(final long workTaskId) {
    final List<WorkTask> workTasks = new ArrayList<>();

    final String sql = SqlScripts.fromResources("get_work_task_by_id.sql");

    try (
      final Connection conn = DriverManager.getConnection(DbProvider.getJdbc());
      final PreparedStatement statement = conn.prepareStatement(sql)
    ) {
      statement.setLong(1, workTaskId);

      final ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        final WorkTask workTask = getNextWorkTask(rs);
        workTasks.add(workTask);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return workTasks.isEmpty() ? null : workTasks.get(0);
  }

  public static long deleteWorkTask(final long workTaskId) {
    int numRowsAffected = -1;

    final String sql = SqlScripts.fromResources("sql/delete_work_task_by_id.sql");

    try (
      final Connection conn = DriverManager.getConnection(DbProvider.getJdbc());
      final PreparedStatement statement = conn.prepareStatement(sql)
    ) {
      statement.setLong(1, workTaskId);

      numRowsAffected = statement.executeUpdate();
    } catch (final SQLException e) {
      throw new RuntimeException(e);
    }

    return numRowsAffected == 1 ? workTaskId : -1;
  }

  public static List<WorkTask> getAllWorkTasks() {
    final List<WorkTask> workTasks = new ArrayList<>();

    final String sql = SqlScripts.fromResources("sql/get_all_work_tasks.sql");

    try (
      final Connection conn = DriverManager.getConnection(DbProvider.getJdbc());
      final PreparedStatement statement = conn.prepareStatement(sql)
    ) {
      final ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        final WorkTask workTask = getNextWorkTask(rs);
        workTasks.add(workTask);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return Collections.unmodifiableList(workTasks);
  }

  public static List<WorkTask> getWorkTasks(final int year, final int month) {
    final List<WorkTask> workTasks = new ArrayList<>();
    final String startDate = new StringBuilder(Integer.toString(year)).append('-')
      .append(String.format("%1$2s", month).replaceAll(" ", "0"))
      .append("-").append("01").toString();
    final String endDate = new StringBuilder(Integer.toString(year)).append('-')
      .append(String.format("%1$2s", month).replaceAll(" ", "0"))
      .append("-").append(getMaxNumberOfDaysForMonth(year, month)).toString();

    final String sql = SqlScripts.fromResources("sql/get_work_tasks_by_year_and_month.sql");

    try (
      final Connection conn = DriverManager.getConnection(DbProvider.getJdbc());
      final PreparedStatement statement = conn.prepareStatement(sql)
    ) {
      statement.setString(1, startDate);
      statement.setString(2, endDate);

      final ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        final WorkTask workTask = getNextWorkTask(rs);
        workTasks.add(workTask);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return Collections.unmodifiableList(workTasks);
  }

  public static boolean endWorkLastWorkTask() {
    final long lastWorkTaskId = getLastWorkTaskId();

    int numRowsAffected = 0;

    if (lastWorkTaskId > 0) {
      final String sql = SqlScripts.fromResources("sql/end_work_task.sql");

      try (
        final Connection conn = DriverManager.getConnection(DbProvider.getJdbc());
        final PreparedStatement statement = conn.prepareStatement(sql)
      ) {
        statement.setLong(1, lastWorkTaskId);

        numRowsAffected = statement.executeUpdate();
      } catch (final SQLException e) {
        throw new RuntimeException(e);
      }

      return numRowsAffected == 1;
    }

    return false;
  }

  private static WorkTask getNextWorkTask(final ResultSet rs) throws SQLException {
    final long id = rs.getLong("id");
    final String strStartDateTime = rs.getString("start_datetime");
    final LocalDateTime startDateTime = LocalDateTime.parse(strStartDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    final String description = rs.getString("description");
    final String requestor = rs.getString("requestor");
    final boolean planned = rs.getString("planned").equals("Y");
    final String project = rs.getString("project");
    final String category = rs.getString("category");
    final String strEndDateTime = rs.getString("end_datetime");
    final LocalDateTime endDateTime = (strEndDateTime != null) ?
      LocalDateTime.parse(strEndDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME) :
        null;

    final WorkTask workTask = WorkTask.builder()
      .id(id)
      .startDateTime(startDateTime)
      .description(description)
      .requestor(requestor)
      .isPlanned(planned)
      .project(project)
      .category(category)
      .endDateTime(endDateTime)
      .build();

    return workTask;
  }

  private static long getLastWorkTaskId() {
    long id = -1L;

    final String sql = SqlScripts.fromResources("sql/get_last_work_task_id.sql");

    try (
      final Connection conn = DriverManager.getConnection(DbProvider.getJdbc());
      final PreparedStatement statement = conn.prepareStatement(sql)
    ) {
      final ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        final Long maxId = rs.getLong("max_id");

        if (maxId != null && maxId > 0) {
          id = maxId;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return id;
  }

  private static LocalDateTime fromDate(final Date date) {
    return date == null ? null : date
      .toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();
  }

  private static int getMaxNumberOfDaysForMonth(final int year, final int month) {
    switch (month) {
      case 2: return isLeapYear(year) ? 29 : 28;
      case 4: case  6: case 9: case 11: return 30;
      default: return 31;
    }
  }

  private static boolean isLeapYear(final int year) {
    return year % 4 == 0
      || year % 400 == 0
      && year % 100 != 0;
  }

}
