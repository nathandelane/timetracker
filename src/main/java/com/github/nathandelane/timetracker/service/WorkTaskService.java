package com.github.nathandelane.timetracker.service;

import com.github.nathandelane.timetracker.model.WorkTask;
import com.github.nathandelane.timetracker.model.WorkTaskIdGenerator;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log
public class WorkTaskService {

  private static WorkTaskIdGenerator ID_GENERATOR;

  private static List<WorkTask> WORK_TASKS;

  static {
    WORK_TASKS = new ArrayList<>();
    ID_GENERATOR = new WorkTaskIdGenerator(0L);
  }

  public static WorkTask saveWorkTask(final WorkTask workTask) {
    final long workTaskId = ID_GENERATOR.id.getAndIncrement();
    final WorkTask updatedWorkTask = WorkTask.builder()
      .id(workTaskId)
      .startDateTime(workTask.startDateTime)
      .description(workTask.description)
      .whoCausedIt(workTask.whoCausedIt)
      .isPlanned(workTask.isPlanned)
      .actionItem(workTask.actionItem)
      .categoryOfWork(workTask.categoryOfWork)
      .project(workTask.project)
      .build();

    WORK_TASKS.add(updatedWorkTask);

    log.info("Created workTask: " + updatedWorkTask);

    return updatedWorkTask;
  }

  public static WorkTask findWorkTask(final long id) {
    WorkTask workTask = null;

    for (final WorkTask nextWorkTask : WORK_TASKS) {
      if (Long.compare(nextWorkTask.id, id) == 0) {
        workTask = nextWorkTask;

        break;
      }
    }

    return workTask;
  }

  public static WorkTask updateWorkTask(final WorkTask original, final WorkTask updates) {
    final LocalDateTime startDateTime = (updates.startDateTime == null) ? original.startDateTime : updates.startDateTime;
    final String description = (updates.description == null || updates.description.trim().equals("")) ? original.description : updates.description;
    final Integer whoCausedIt = (Integer.compare(updates.whoCausedIt, original.whoCausedIt) != 0) ? updates.whoCausedIt : original.whoCausedIt;
    final Boolean isPlanned = (Boolean.compare(updates.isPlanned, original.isPlanned) != 0) ? updates.isPlanned : original.isPlanned;
    final String actionItem = (updates.actionItem == null || updates.actionItem.trim().equals("")) ? original.actionItem : updates.actionItem;
    final Integer categoryOfWork = (Integer.compare(updates.categoryOfWork, original.categoryOfWork) != 0) ? updates.categoryOfWork : original.categoryOfWork;
    final Integer project = (Integer.compare(updates.project, original.project) != 0) ? updates.project : original.project;

    final WorkTask updatedWorkTask = WorkTask.builder()
      .id(original.id)
      .startDateTime(startDateTime)
      .description(description)
      .whoCausedIt(whoCausedIt)
      .isPlanned(isPlanned)
      .actionItem(actionItem)
      .categoryOfWork(categoryOfWork)
      .project(project)
      .build();

    final int index = WORK_TASKS.indexOf(original);

    if (index != -1) {
      WORK_TASKS.set(index, updatedWorkTask);
    }
    else {
      throw new IllegalStateException("Could not update work task: " + original);
    }

    return updatedWorkTask;
  }

  public static boolean deleteWorkTask(final long id) {
    final WorkTask workTaskToDelete = findWorkTask(id);

    if (workTaskToDelete != null) {
      WORK_TASKS.remove(workTaskToDelete);

      return true;
    }
    else {
      return false;
    }
  }

  public static List<WorkTask> getAllWorkTasks() {
    return WORK_TASKS;
  }

}
