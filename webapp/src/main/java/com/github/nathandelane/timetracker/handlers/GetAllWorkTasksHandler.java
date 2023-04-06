package com.github.nathandelane.timetracker.handlers;

import com.github.nathandelane.timetracker.data.*;
import com.github.nathandelane.timetracker.model.*;
import com.google.gson.*;
import spark.*;

import java.util.*;

public class GetAllWorkTasksHandler implements Route {

  private final Gson gson;

  private final WorkTaskDao workTaskDao;

  public GetAllWorkTasksHandler(final Gson gson, final WorkTaskDao workTaskDao) {
    this.gson = gson;
    this.workTaskDao = workTaskDao;
  }

  @Override
  public Object handle(final Request request, final Response response) throws Exception {
    final List<WorkTask> workTaskList = workTaskDao.getAllWorkTasks();
    final String responseBody = gson.toJson(workTaskList);

    response.header("Content-Length", String.valueOf(responseBody.length()));
    response.body(responseBody);
    response.status(200);
    response.type("application/json");

    return response;
  }

}
