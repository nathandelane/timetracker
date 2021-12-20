package com.github.nathandelane.timetracker.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeJsonDeserializer implements JsonDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    if (jsonElement != null) {
      final LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(jsonElement.getAsLong(), 0, ZoneOffset.ofHours(-6));

      return localDateTime;
    }
    else {
      return null;
    }
  }

}
