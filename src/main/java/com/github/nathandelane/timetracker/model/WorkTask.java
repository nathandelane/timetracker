package com.github.nathandelane.timetracker.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class WorkTask {

  public final Long id;

  @NonNull
  public final LocalDateTime startDateTime;

  @NonNull
  public final String description;

  @NonNull
  public final Integer whoCausedIt;

  @NonNull
  public final Boolean isPlanned;

  public final String actionItem;

  @NonNull
  public final Integer categoryOfWork;

  public final Integer project;

}
