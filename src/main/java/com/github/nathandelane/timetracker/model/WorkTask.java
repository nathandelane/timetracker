package com.github.nathandelane.timetracker.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class WorkTask {

  public final Long id;

  public final LocalDateTime startDateTime;

  @NonNull
  public final String description;

  @NonNull
  public final String requestor;

  @NonNull
  public final Boolean isPlanned;

  @NonNull
  public final String category;

  public final String project;

  public final LocalDateTime endDateTime;

}
