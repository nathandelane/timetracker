package com.github.nathandelane.timetracker.config;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

@Slf4j
public final class ApplicationConfiguration {

  private static final ApplicationConfiguration INSTANCE = new ApplicationConfiguration();

  private final Map<String, Object> configPairs;

  private ApplicationConfiguration() {
    configPairs = loadAppConfig();
  }

  private Map<String, Object> loadAppConfig() {
    final Yaml yaml = new Yaml();

    try (final InputStream is = getClass().getClassLoader().getResourceAsStream("application.yml")) {
      final Map<String, Object> m = yaml.load(is);

      return Collections.unmodifiableMap(m);
    } catch (final IOException e) {
      log.error("Exception caught trying to load application.yml", e);
    }

    return Collections.EMPTY_MAP;
  }

  public Object getConfigValue(final String key) {
    return configPairs.get(key);
  }

  public static ApplicationConfiguration get() {
    return INSTANCE;
  }

}
