package com.antfitness.ant.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddExerciseToWorkoutRequest {

    @NotNull
    private Long exerciseId;

    @Min(1)
    private int sets;

    @Min(1)
    private int reps;
}
