package com.github.nathandelane.timetracker.deps;

import com.github.nathandelane.timetracker.data.*;
import com.github.nathandelane.timetracker.handlers.*;
import com.google.gson.*;

import java.util.function.Consumer;

public final class Dependencies {

  private static DataSource DATA_SOURCE = null;

  public static synchronized DataSource getDataSource() {
    if (DATA_SOURCE == null) {
      try {
        DATA_SOURCE = new DataSource();
      } catch (final ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }

    return DATA_SOURCE;
  }

  public static WorkTaskDao getWorkTaskDao() {
    return new WorkTaskDao(getDataSource());
  }

  public static Gson getGson() {
    return new GsonBuilder().create();
  }

  public static Consumer<Exception> getExceptionHandler() {
    return new ExceptionHandler(getGson());
  }

  public static GetAllWorkTasksHandler getGetAllWorkTasksHandler() {
    return new GetAllWorkTasksHandler(getGson(), getWorkTaskDao());
  }

  public static GetWorkTaskByIdHandler getGetWorkTaskByIdHanddler() {
    return new GetWorkTaskByIdHandler(getGson(), getWorkTaskDao());
  }

  public static CreateWorkTaskHandler getCreateWorkTaskHandler() {
    return new CreateWorkTaskHandler(getGson(), getWorkTaskDao());
  }

}
