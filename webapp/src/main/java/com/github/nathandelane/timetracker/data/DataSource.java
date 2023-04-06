package com.github.nathandelane.timetracker.data;

import java.sql.*;

import static com.github.nathandelane.timetracker.deps.Configuration.getJdbcClassName;
import static com.github.nathandelane.timetracker.deps.Configuration.getJdbcString;

public class DataSource {

  public DataSource() throws ClassNotFoundException {
    Class.forName(getJdbcClassName());
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(getJdbcString());
  }

}
