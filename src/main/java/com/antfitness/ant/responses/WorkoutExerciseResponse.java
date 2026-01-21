package com.antfitness.ant.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkoutExerciseResponse {

    private Long id;
    private String exerciseName;
    private int sets;
    private int reps;
    private int orderIndex;
}
