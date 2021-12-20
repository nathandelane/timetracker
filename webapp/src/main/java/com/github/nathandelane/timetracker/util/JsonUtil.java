package com.github.nathandelane.timetracker.util;

import com.github.nathandelane.timetracker.model.LocalDateTimeJsonDeserializer;
import com.github.nathandelane.timetracker.model.LocalDateTimeJsonSerializer;
import com.github.nathandelane.timetracker.model.WorkTask;
import com.google.gson.*;

import java.time.LocalDateTime;

public final class JsonUtil {

  private static final Gson GSON = new GsonBuilder()
    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonSerializer())
    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonDeserializer())
    .create();

  public static final String toJson(final Object data) {
    return GSON.toJson(data);
  }

  public static final WorkTask fromJson(final String json) {
    return GSON.fromJson(json, WorkTask.class);
  }

}
