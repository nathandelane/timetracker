package com.github.nathandelane.timetracker.data;

public final class DbProvider {

  public static final String DB_KEY = "com.github.nathandelane.timetracker.jdbc";

  private DbProvider() { }

  public static String getJdbc() {
    if (System.getProperty(DB_KEY) != null) {
      return System.getProperty(DB_KEY);
    }

    return "jdbc:sqlite:timetracker.db";
  }

}
