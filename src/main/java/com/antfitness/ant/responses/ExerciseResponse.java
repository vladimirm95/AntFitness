package com.antfitness.ant.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExerciseResponse {

    private Long id;
    private String name;
    private String description;
    private String muscleGroup;
    private String category;
    private String exerciseType;
    private String equipment;
    private String difficulty;
    private String movementPattern;
}