package com.github.nathandelane.timetracker.rest;

import com.github.nathandelane.timetracker.model.WorkTask;
import com.github.nathandelane.timetracker.service.WorkTaskService;
import com.github.nathandelane.timetracker.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDateTime;

public class WorkTaskController {

  public static Route createWorkTask = (Request request, Response response) -> {
    final WorkTask responseWorkTask;

    if (request.body() != null) {
      final WorkTask workTask = JsonUtil.fromJson(request.body());

      response.status(200);
      response.type("application/json");

      responseWorkTask = WorkTaskService.saveWorkTask(workTask);
    }
    else {
      responseWorkTask = WorkTask.builder()
        .build();
    }

    return JsonUtil.toJson(responseWorkTask);
  };

  public static Route updateWorkTask = (Request request, Response response) -> {
    final String idAsString = request.params(":id");
    final long id = Long.parseLong(idAsString);
    final WorkTask updates = JsonUtil.fromJson(request.body());

    response.type("application/json");

    WorkTask foundWorkTask = WorkTaskService.findWorkTask(id);

    if (foundWorkTask == null) {
      response.status(404);

      return "{ \"error\" : \"WorkTask with ID " + id + " not found!\" }";
    }
    else {
      final WorkTask workTask = WorkTaskService.updateWorkTask(foundWorkTask, updates);

      response.status(200);

      return JsonUtil.toJson(workTask);
    }
  };

  public static Route deleteWorkTask = (Request request, Response response) -> {
    final String idAsString = request.params(":id");
    final long id = Long.parseLong(idAsString);

    if (WorkTaskService.deleteWorkTask(id)) {
      response.status(201);

      return "{ \"success\" : \"WorkTask with ID " + id + " deleted\" }";
    }
    else {
      response.status(404);

      return "{ \"error\" : \"WorkTask with ID " + id + " not found!\" }";
    }
  };

  public static Route getAllWorkTasks = (Request request, Response response) -> {
    response.status(200);
    response.type("application/json");

    return JsonUtil.toJson(WorkTaskService.getAllWorkTasks());
  };

}
