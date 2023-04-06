package com.github.nathandelane.timetracker.filters;

import org.slf4j.*;
import spark.*;

import java.time.LocalDateTime;

import static com.github.nathandelane.timetracker.deps.Configuration.getDateAndTimeFormat;
import static com.github.nathandelane.timetracker.deps.Configuration.getZoneOffset;
import static com.github.nathandelane.timetracker.filters.RequestAttributes.START_TIME_MILLIS;

public final class Filters {

  private static final Logger LOGGER = LoggerFactory.getLogger(Filters.class);

  private Filters() { }

  public static Filter BEFORE_ADD_REQUEST_START_TIME_MILLIS = (final Request request, final Response response) -> {
    request.attribute(START_TIME_MILLIS, LocalDateTime.now().toInstant(getZoneOffset()).toEpochMilli());
  };

  public static Filter AFTER_LOG_REQUEST_AND_TIME_IN_MILLIS = (final Request request, final Response response) -> {
    final long timeInMillisNow = LocalDateTime.now().toInstant(getZoneOffset()).toEpochMilli();
    final long startTimeInMillis = request.attribute(START_TIME_MILLIS);
    final long difference = (timeInMillisNow - startTimeInMillis);
    final StringBuilder logBuilder = new StringBuilder(LocalDateTime.now().format(getDateAndTimeFormat()))
      .append(" ").append(request.requestMethod())
      .append(" ").append(request.pathInfo())
      .append(" ").append("HTTP/1.1")
      .append(" ").append(response.status())
      .append(" ").append(difference);

    LOGGER.info(logBuilder.toString());
  };

  public static Filter AFTER_ADD_GZIP_HEADER = (final Request request, final Response response) -> {
    response.header("Content-Encoding", "gzip");
  };

}
