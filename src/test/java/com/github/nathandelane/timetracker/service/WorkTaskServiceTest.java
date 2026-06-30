package com.github.nathandelane.timetracker.service;

import com.github.nathandelane.timetracker.data.DbProvider;
import com.github.nathandelane.timetracker.util.WorkTaskTestDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

  }

}
