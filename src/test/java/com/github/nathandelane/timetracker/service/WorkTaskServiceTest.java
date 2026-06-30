package com.github.nathandelane.timetracker.service;

import com.github.nathandelane.timetracker.data.DbProvider;
import com.github.nathandelane.timetracker.data.WorkTaskDao;
import com.github.nathandelane.timetracker.model.WorkTask;
import com.github.nathandelane.timetracker.util.WorkTaskTestDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkTaskServiceTest {

  @BeforeAll
  public static void setupDbProvider() {
    System.setProperty(DbProvider.DB_NAME_KEY, "timetracker_test.db");
  }

  @BeforeEach
  public void clearDb() {
    WorkTaskTestDao.deleteAllWorkTasks();
  }

  @Test
  public void testStartDay() {
    WorkTaskService.startDay();

    final List<WorkTask> workTaskList = WorkTaskDao.getAllWorkTasks();

    assertFalse(workTaskList.isEmpty());
    assertTrue(workTaskList.size() == 1);
  }

  @Test
  public void testStartDayAddTaskAfterSleep() throws InterruptedException {
    WorkTaskService.startDay();

    Thread.sleep(10_000);

    final WorkTask doSomething = WorkTask.builder()
      .category("Admin")
      .description("Do something")
      .requestor("I")
      .isPlanned(true)
      .build();

    WorkTaskService.saveWorkTask(doSomething);

    final List<WorkTask> workTaskList = WorkTaskDao.getAllWorkTasks();

    assertFalse(workTaskList.isEmpty());
    assertTrue(workTaskList.size() == 2);
  }

}
