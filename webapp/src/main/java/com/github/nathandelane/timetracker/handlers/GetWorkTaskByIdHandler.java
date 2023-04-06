package com.github.nathandelane.timetracker.handlers;

import com.github.nathandelane.timetracker.data.*;
import com.github.nathandelane.timetracker.model.*;
import com.google.gson.*;
import spark.*;

public class GetWorkTaskByIdHandler implements Route {

  private final Gson gson;

  private final WorkTaskDao workTaskDao;

  public GetWorkTaskByIdHandler(final Gson gson, final WorkTaskDao workTaskDao) {
    this.gson = gson;
    this.workTaskDao = workTaskDao;
  }

  @Override
  public Object handle(final Request request, final Response response) throws Exception {
    final String idPathParam = request.params("id");
    final long workTaskId = Long.valueOf(idPathParam);
    final WorkTask workTask = workTaskDao.getWorkTaskById(workTaskId);
    final String responseBody = gson.toJson(workTask);

    response.header("Content-Length", String.valueOf(responseBody.length()));
    response.body(responseBody);
    response.status(200);
    response.type("application/json");

    return response;
  }

}
