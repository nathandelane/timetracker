package com.github.nathandelane.timetracker.deps;

import java.time.*;
import java.time.format.*;
import java.util.*;

public final class Configuration {

  private Configuration() { }

  public static int getPortNumber() {
    return 9093;
  }

  public static int getNumberOfThreads() {
    return 200;
  }

  public static String getJdbcClassName() {
    return "org.sqlite.JDBC";
  }

  public static String getSqliteFileName() {
    return "timetracker.db";
  }

  public static String getJdbcString() {
    return new StringBuilder("jdbc:sqlite:")
      .append(getSqliteFileName())
      .toString();
  }

  public static TimeZone getTimeZone() {
    return TimeZone.getTimeZone("America/Denver");
  }

  public static ZoneOffset getZoneOffset() {
    return getTimeZone().toZoneId().getRules().getOffset(LocalDateTime.now());
  }

  public static DateTimeFormatter getDateAndTimeFormat() {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  }

}
