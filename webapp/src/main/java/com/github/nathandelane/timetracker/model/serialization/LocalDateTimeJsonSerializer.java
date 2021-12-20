package com.github.nathandelane.timetracker.model.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeJsonSerializer implements JsonSerializer<LocalDateTime> {

  @Override
  public JsonElement serialize(final LocalDateTime localDateTime, final Type type, final JsonSerializationContext jsonSerializationContext) {
    if (localDateTime != null) {
      return new JsonPrimitive(localDateTime.toEpochSecond(ZoneOffset.ofHours(-6)));
    }
    else {
      return null;
    }
  }

}
