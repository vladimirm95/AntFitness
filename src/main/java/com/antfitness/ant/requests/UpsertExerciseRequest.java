package com.antfitness.ant.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpsertExerciseRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private String muscleGroup;

    @NotNull
    private String category;

    @NotNull
    private String exerciseType;

    @NotNull
    private String equipment;

    @NotNull
    private String difficulty;

    @NotNull
    private String movementPattern;
}