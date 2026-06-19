package com.github.nathandelane.timetracker.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class SqlScripts {

  private SqlScripts() { }

  public static String fromResources(final String resourceFilePath) {
    final StringBuilder sb = new StringBuilder();

    try (
      final InputStream resourceInputStream = SqlScripts.class.getClassLoader().getResourceAsStream(resourceFilePath);
      final InputStreamReader isr = new InputStreamReader(resourceInputStream);
      final BufferedReader br = new BufferedReader(isr);
    ) {
      String line = null;

      while ((line = br.readLine()) != null) {
        sb.append(line).append(System.lineSeparator());
      }
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }

    return sb.toString();
  }

}
