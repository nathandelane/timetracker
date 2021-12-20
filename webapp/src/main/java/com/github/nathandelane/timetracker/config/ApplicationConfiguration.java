package com.github.nathandelane.timetracker.config;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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
    final List<String> keyList = new ArrayList<>();

    if (key.contains(".")) {
      String[] keys = key.split("\\.");

      keyList.addAll(Arrays.asList(keys));
    }
    else {
      keyList.add(key);
    }

    Object value = null;

    for (final String nextKey : keyList) {
      if (value == null) {
        value = configPairs.get(nextKey);
      }
      else {
        final Object nextValue = ((LinkedHashMap) value).get(nextKey);

        value = nextValue;
      }
    }

    return value;
  }

  public static ApplicationConfiguration get() {
    return INSTANCE;
  }

}
