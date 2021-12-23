package com.github.nathandelane.timetracker.data;

import com.github.nathandelane.timetracker.model.WorkTask;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public abstract class CsvFileSerializer implements DataSerializer {

  private static final String DIRECTORY = (System.getProperty("user.home") + File.pathSeparator + ".timetracker");

  private final String filename;

  protected CsvFileSerializer(final String filename) {
    this.filename = filename;

    final Path path = Path.of(DIRECTORY);

    if (!Files.exists(path)) {
      try {
        Files.createDirectory(path);
      } catch (final IOException e) {
        log.error("Exception caught.", e);
      }
    }
  }

  @Override
  public void write(final List<WorkTask> workTasks) {
    for (final WorkTask nextWorkTask : workTasks) {

    }
  }

  @Override
  public List<WorkTask> read() {
    return null;
  }

}
