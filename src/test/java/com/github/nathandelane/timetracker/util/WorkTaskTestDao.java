package com.github.nathandelane.timetracker.util;

import com.github.nathandelane.timetracker.data.DbProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class WorkTaskTestDao {

  private WorkTaskTestDao() { }

  public static void deleteALlWorkTasks() {
    final String sql = SqlScripts.fromResources("sql/delete_all_work_tasks.sql");

    try (
      final Connection conn = DriverManager.getConnection(DbProvider.getJdbc());
      final PreparedStatement statement = conn.prepareStatement(sql)
    ) {
      statement.executeUpdate();
    } catch (final SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
