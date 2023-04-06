package com.github.nathandelane.timetracker;

import static com.github.nathandelane.timetracker.deps.Dependencies.*;
import static com.github.nathandelane.timetracker.deps.Configuration.*;
import static com.github.nathandelane.timetracker.filters.Filters.*;
import static spark.Spark.*;

public class Application {

  private Application() {
    initializeApplication();
    addFilters();
    addResourceMappings();
  }

  private void initializeApplication() {
    initExceptionHandler(getExceptionHandler());
    port(getPortNumber());
    threadPool(getNumberOfThreads());
  }

  private void addFilters() {
    before("*", BEFORE_ADD_REQUEST_START_TIME_MILLIS);
    after("*", AFTER_LOG_REQUEST_AND_TIME_IN_MILLIS);
    after("*", AFTER_ADD_GZIP_HEADER);
  }

  private void addResourceMappings() {
    get("/worktasks", getGetAllWorkTasksHandler());
    get("/worktasks/:id", getGetWorkTaskByIdHanddler());
    put("/worktasks/:start_date_and_time", "application/json", getCreateWorkTaskHandler());
  }

  public static void main(final String[] args) {
    new Application();
  }

}
