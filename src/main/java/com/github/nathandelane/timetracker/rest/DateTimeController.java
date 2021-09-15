package com.github.nathandelane.timetracker.rest;

import com.github.nathandelane.timetracker.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDateTime;

public class DateTimeController {

  public static Route getCurrentTime = (Request request, Response response) -> {
    final LocalDateTime now = LocalDateTime.now();;

    return JsonUtil.toJson(now);
  };

}
