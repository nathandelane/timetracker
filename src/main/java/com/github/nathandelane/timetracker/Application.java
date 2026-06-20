package com.github.nathandelane.timetracker;

import com.github.nathandelane.timetracker.rest.DateTimeController;
import com.github.nathandelane.timetracker.rest.HealthController;
import com.github.nathandelane.timetracker.rest.WorkTaskController;

import static spark.Spark.*;

/**
 * https://sparkjava.com/tutorials/application-structure
 */
public class Application {

  public static void main(final String[] args) {
    port(8080);

    get("/health", HealthController.getApplicationHealth);

    get("/workTasks", WorkTaskController.getWorkTasksForCurrentMonth);
    get("/workTasks/:year/:month", WorkTaskController.getWorkTasks);
    put("/workTask", "application/json", WorkTaskController.createWorkTask);
    post("/workTask/:id", "application/json", WorkTaskController.updateWorkTask);
    delete("/workTask/:id/delete", "application/json", WorkTaskController.deleteWorkTask);

    get("/now", DateTimeController.getCurrentTime);
  }

}
