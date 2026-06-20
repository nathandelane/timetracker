package com.github.nathandelane.timetracker.data;

import com.github.nathandelane.timetracker.model.WorkTask;
import com.github.nathandelane.timetracker.util.WorkTaskTestDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkTaskDaoTest {

  @BeforeAll
  public static void setupDbProvider() {
    System.setProperty(DbProvider.DB_NAME_KEY, "timetracker_test.db");
  }

  @BeforeEach
  public void clearDb() {
    WorkTaskTestDao.deleteAllWorkTasks();
  }

  @Test
  public void testSaveWorkTasksSingleTask() {
    final LocalDateTime localDateTime = LocalDateTime.now();
    final List<WorkTask> workTasks = new ArrayList<>();
    final WorkTask workTask = WorkTask.builder()
      .isPlanned(true)
      .category("Strategic Project")
      .description("Start day")
      .requestor("I")
      .startDateTime(localDateTime)
      .build();

    workTasks.add(workTask);

    WorkTaskDao.saveWorkTasks(workTasks);

    workTasks.clear();

    assertTrue(workTasks.isEmpty());

    workTasks.addAll(WorkTaskDao.getWorkTasks(localDateTime.getYear(), localDateTime.getMonthValue()));

    assertTrue(1 <= workTasks.size());
  }

}
