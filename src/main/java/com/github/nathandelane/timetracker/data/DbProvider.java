package com.github.nathandelane.timetracker.data;

import com.github.nathandelane.timetracker.util.SqlScripts;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class DbProvider {

  public static final String DB_NAME_KEY = "com.github.nathandelane.timetracker.db.name";

  private static final String DEFAULT_DB_NAME = "timetracker.db";

  private DbProvider() { }

  public static String getJdbc() {
    final String dbName;

    if (System.getProperty(DB_NAME_KEY) != null) {
      dbName = System.getProperty(DB_NAME_KEY);
    }
    else {
      dbName = DEFAULT_DB_NAME;
    }

    final String jdbc = createDbAndExecuteDDLIfNotExists(dbName);

    return jdbc;
  }

  private static String createDbAndExecuteDDLIfNotExists(final String dbName) {
    final String jdbc = String.format("jdbc:sqlite:%s", dbName);
    final File localDb = new File(dbName);

    if (!localDb.exists()) {
      final String sql = SqlScripts.fromResources("sql/ddl.sql");

      try (
        final Connection conn = DriverManager.getConnection(jdbc);
        final PreparedStatement statement = conn.prepareStatement(sql)
      ) {
        statement.executeUpdate();
      } catch (final SQLException e) {
        throw new RuntimeException(e);
      }
    }

    return jdbc;
  }


}
