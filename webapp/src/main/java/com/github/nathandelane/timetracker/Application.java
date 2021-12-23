package com.github.nathandelane.timetracker;

import com.github.nathandelane.timetracker.config.ApplicationConfiguration;
import com.github.nathandelane.timetracker.rest.DateTimeController;
import com.github.nathandelane.timetracker.rest.HealthController;
import com.github.nathandelane.timetracker.rest.WorkTaskController;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.LogManager;

import static spark.Spark.*;

/**
 * https://sparkjava.com/tutorials/application-structure
 */
@Slf4j
public class Application {

  static {
    String path = Application.class.getClassLoader().getResource("logging.properties").getFile();
    System.setProperty("java.util.logging.config.file", path);
  }

  private static final String LISTENER_PORT = "timetracker.listener.port";

  private static int port;

  static {
    final ApplicationConfiguration appConf = ApplicationConfiguration.get();
    final Object portObj = appConf.getConfigValue(LISTENER_PORT);

    if (portObj != null) {
      final String strPort = portObj.toString();

      port = Integer.parseInt(strPort);
    }
  }

  public static void main(final String[] args) {
    log.info("Starting application...");

    port(port);

    get("/health", HealthController.getApplicationHealth);

    get("/workTasks", WorkTaskController.getAllWorkTasks);
    put("/workTask", "application/json", WorkTaskController.createWorkTask);
    post("/workTask/:id", "application/json", WorkTaskController.updateWorkTask);
    delete("/workTask/:id/delete", "application/json", WorkTaskController.deleteWorkTask);

    get("/now", DateTimeController.getCurrentTime);

    //ApplicationConfiguration.get().getConfigValue("test");
    ApplicationConfiguration.get().getConfigValue("");
  }

}
