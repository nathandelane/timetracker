package com.github.nathandelane.timetracker.handlers;

import com.github.nathandelane.timetracker.data.*;
import com.github.nathandelane.timetracker.model.*;
import com.google.gson.*;
import spark.*;


import java.time.*;

import static com.github.nathandelane.timetracker.deps.Configuration.*;

/**
 * Path parameters:
 *
 * start_date_and_time: format should be <pre>YYYY-MM-DD HH24:MI</pre>.
 * Spaces should be escaped to %20.
 * Colons should be escaped to %3A.
 */
public class CreateWorkTaskHandler implements Route {

  private final Gson gson;

  private final WorkTaskDao workTaskDao;

  public CreateWorkTaskHandler(final Gson gson, final WorkTaskDao workTaskDao) {
    this.gson = gson;
    this.workTaskDao = workTaskDao;
  }

  @Override
  public Object handle(final Request request, final Response response) {
    final String startDateAndTimeParamValue = request.params("start_date_and_time");
    final LocalDateTime startDateAndTimeAsDate = LocalDateTime.parse(startDateAndTimeParamValue, getDateAndTimeFormat());
    final long startDateAndTime = startDateAndTimeAsDate.toEpochSecond(getZoneOffset());

    final WorkTask parsedWorkTask = gson.fromJson(request.body(), WorkTask.class);
    final WorkTask workTask = new WorkTask(startDateAndTime, parsedWorkTask);
    final int updatedRecords = workTaskDao.createWorkTask(workTask);
    final String responseBody = Integer.toString(updatedRecords);

    response.header("Content-Length", String.valueOf(responseBody.length()));
    response.body(responseBody);
    response.status(200);
    response.type("text/plain");

    return response;
  }

}
