package com.github.nathandelane.timetracker.handlers;

import com.google.gson.Gson;
import org.slf4j.*;

import java.util.function.*;

public class ExceptionHandler implements Consumer<Exception> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

  private final Gson gson;

  public ExceptionHandler(final Gson gson) {
    this.gson = gson;
  }

  @Override
  public void accept(final Exception e) {
    LOGGER.error("Application could not be started.", e);

    System.exit(100);
  }

}
