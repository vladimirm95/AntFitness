package com.antfitness.ant.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class WorkoutPlanResponse {

    private Long id;
    private LocalDate date;
    private boolean completed;
    private List<WorkoutExerciseResponse> exercises;
}
