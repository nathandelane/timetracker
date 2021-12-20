package com.github.nathandelane.timetracker.model;

import java.util.concurrent.atomic.AtomicLong;

public class WorkTaskIdGenerator {

  public final AtomicLong id;

  public WorkTaskIdGenerator(final Long initialId) {
    this.id = new AtomicLong(initialId);
  }

}
