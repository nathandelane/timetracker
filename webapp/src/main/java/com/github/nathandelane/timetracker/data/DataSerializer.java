package com.github.nathandelane.timetracker.data;

import com.github.nathandelane.timetracker.model.WorkTask;

import java.util.List;

public interface DataSerializer {

  public void write(final List<WorkTask> workTasks);

  public List<WorkTask> read();

}
