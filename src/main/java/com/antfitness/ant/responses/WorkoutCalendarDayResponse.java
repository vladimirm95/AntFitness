package com.antfitness.ant.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class WorkoutCalendarDayResponse {
    private LocalDate date;
    private boolean completed;
}
