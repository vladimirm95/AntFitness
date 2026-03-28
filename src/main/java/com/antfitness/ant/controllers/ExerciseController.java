package com.antfitness.ant.controllers;

import com.antfitness.ant.model.*;
import com.antfitness.ant.responses.ExerciseResponse;
import com.antfitness.ant.services.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    public List<ExerciseResponse> all(
            @RequestParam(required = false) String muscleGroup,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String equipment,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String exerciseType
    ) {
        List<Exercise> exercises;

        if (muscleGroup != null && !muscleGroup.isBlank()) {
            MuscleGroup mg = MuscleGroup.valueOf(muscleGroup.trim().toUpperCase());
            exercises = exerciseService.findByMuscleGroup(mg);
        } else if (category != null && !category.isBlank()) {
            ExerciseCategory c = ExerciseCategory.valueOf(category.trim().toUpperCase());
            exercises = exerciseService.findByCategory(c);
        } else if (equipment != null && !equipment.isBlank()) {
            Equipment eq = Equipment.valueOf(equipment.trim().toUpperCase());
            exercises = exerciseService.findByEquipment(eq);
        } else if (difficulty != null && !difficulty.isBlank()) {
            Difficulty d = Difficulty.valueOf(difficulty.trim().toUpperCase());
            exercises = exerciseService.findByDifficulty(d);
        } else if (exerciseType != null && !exerciseType.isBlank()) {
            ExerciseType et = ExerciseType.valueOf(exerciseType.trim().toUpperCase());
            exercises = exerciseService.findByExerciseType(et);
        } else {
            exercises = exerciseService.findAll();
        }

        return exercises.stream()
                .map(this::map)
                .toList();
    }

    private ExerciseResponse map(Exercise exercise) {
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getMuscleGroup().name(),
                exercise.getCategory().name(),
                exercise.getExerciseType().name(),
                exercise.getEquipment().name(),
                exercise.getDifficulty().name(),
                exercise.getMovementPattern().name()
        );
    }
}