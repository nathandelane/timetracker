package com.github.nathandelane.timetracker.model;

import java.util.*;

public class WorkTask {

  public final int id;

  public final long startDateAndTime;

  public final String description;

  public final String whoCausedIt;

  public final boolean wasPlanned;

  public final String actionItem;

  public final String categoryOfWork;

  public final String project;

  public WorkTask(
    final int id,
    final long startDateAndTime,
    final String description,
    final String whoCausedIt,
    final boolean wasPlanned,
    final String actionItem,
    final String categoryOfWork,
    final String project
  ) {
    this.id = id;
    this.startDateAndTime = startDateAndTime;
    this.description = description;
    this.whoCausedIt = whoCausedIt;
    this.wasPlanned = wasPlanned;
    this.actionItem = actionItem;
    this.categoryOfWork = categoryOfWork;
    this.project = project;
  }

  public WorkTask(
    final long startDateAndTime,
    final WorkTask workTask
  ) {
    this.id = workTask.id;
    this.startDateAndTime = startDateAndTime;
    this.description = workTask.description;
    this.whoCausedIt = workTask.whoCausedIt;
    this.wasPlanned = workTask.wasPlanned;
    this.actionItem = workTask.actionItem;
    this.categoryOfWork = workTask.categoryOfWork;
    this.project = workTask.project;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WorkTask workTask = (WorkTask) o;
    return id == workTask.id && wasPlanned == workTask.wasPlanned && Long.compare(startDateAndTime, workTask.startDateAndTime) == 0 && description.equals(workTask.description) && whoCausedIt.equals(workTask.whoCausedIt) && Objects.equals(actionItem, workTask.actionItem) && categoryOfWork.equals(workTask.categoryOfWork) && Objects.equals(project, workTask.project);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, startDateAndTime, description, whoCausedIt, wasPlanned, actionItem, categoryOfWork, project);
  }

  @Override
  public String toString() {
    return "WorkTask{" +
      "id=" + id +
      ", startDateAndTime=" + startDateAndTime +
      ", description='" + description + '\'' +
      ", whoCausedIt='" + whoCausedIt + '\'' +
      ", wasPlanned=" + wasPlanned +
      ", actionItem='" + actionItem + '\'' +
      ", categoryOfWork='" + categoryOfWork + '\'' +
      ", project='" + project + '\'' +
      '}';
  }

}
