package com.github.nathandelane.timetracker.rest;

import spark.Request;
import spark.Response;
import spark.Route;

public final class HealthController {

  public static Route getApplicationHealth = (Request request, Response response) -> {
    response.status(200);
    response.type("text/plain");

    return "OK";
  };

}
