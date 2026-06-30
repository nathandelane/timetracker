package com.github.nathandelane.timetracker.service;

import com.github.nathandelane.timetracker.data.WorkTaskDao;
import com.github.nathandelane.timetracker.model.WorkTask;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.util.List;

@Log
public class WorkTaskService {

  public static WorkTask startDay() {
    final LocalDateTime startDayDateTime = LocalDateTime.now();
    final WorkTask startDayWorkTask = WorkTask.builder()
      .startDateTime(startDayDateTime)
      .isPlanned(true)
      .description("Start day")
      .requestor("I")
      .build();

    saveWorkTask(startDayWorkTask);

    log.info("Started work day: " + startDayDateTime);

    return startDayWorkTask;
  }

  public static WorkTask endDay() {
    final LocalDateTime endDayDateTime = LocalDateTime.now();
    final WorkTask startDayWorkTask = WorkTask.builder()
      .startDateTime(endDayDateTime)
      .endDateTime(endDayDateTime)
      .isPlanned(true)
      .description("End day")
      .requestor("I")
      .build();

    saveWorkTask(startDayWorkTask);

    log.info("Ended work day: " + endDayDateTime);

    return startDayWorkTask;
  }

  public static WorkTask saveWorkTask(final WorkTask workTask) {
    final boolean lastWorkTaskEnded = WorkTaskDao.endWorkLastWorkTask();

    if (!lastWorkTaskEnded) {
      log.info("No work task ended.");
    }

    WorkTaskDao.saveWorkTask(workTask);

    log.info("Created workTask: " + workTask);

    return workTask;
  }

  public static WorkTask findWorkTask(final long id) {
    final WorkTask workTask = WorkTaskDao.findWorkTask(id);
    return workTask;
  }

  public static WorkTask updateWorkTask(final WorkTask original, final WorkTask updates) {
    final LocalDateTime startDateTime = (updates.startDateTime == null) ? original.startDateTime : updates.startDateTime;
    final String description = (updates.description == null || updates.description.trim().equals("")) ? original.description : updates.description;
    final String requestor = (!updates.requestor.equals(original.requestor)) ? updates.requestor : original.requestor;
    final Boolean isPlanned = (Boolean.compare(updates.isPlanned, original.isPlanned) != 0) ? updates.isPlanned : original.isPlanned;
    final String categoryOfWork = (!updates.category.equals(original.category)) ? updates.category : original.category;
    final String project = (!updates.project.equals(original.project)) ? updates.project : original.project;

    final WorkTask updatedWorkTask = WorkTask.builder()
      .id(original.id)
      .startDateTime(startDateTime)
      .description(description)
      .requestor(requestor)
      .isPlanned(isPlanned)
      .category(categoryOfWork)
      .project(project)
      .build();

    return updatedWorkTask;
  }

  public static boolean deleteWorkTask(final long id) {
    final WorkTask workTaskToDelete = findWorkTask(id);

    if (workTaskToDelete != null) {
      final long deletedWorkTaskId = WorkTaskDao.deleteWorkTask(id);

      return Long.compare(id, deletedWorkTaskId) == 0;
    }
    else {
      return false;
    }
  }

  public static List<WorkTask> getAllWorkTasks() {
    return WorkTaskDao.getAllWorkTasks();
  }

  public static List<WorkTask> getWorkTasks(final int year, final int month) {
    return WorkTaskDao.getWorkTasks(year, month);
  }

}
