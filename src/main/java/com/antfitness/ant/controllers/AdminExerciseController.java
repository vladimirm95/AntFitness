package com.antfitness.ant.controllers;

import com.antfitness.ant.model.*;
import com.antfitness.ant.requests.UpsertExerciseRequest;
import com.antfitness.ant.responses.ExerciseResponse;
import com.antfitness.ant.security.aop.AdminOnly;
import com.antfitness.ant.services.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/exercises")
@RequiredArgsConstructor
public class AdminExerciseController {

    private final ExerciseService exerciseService;

    @AdminOnly
    @PostMapping
    public ExerciseResponse create(@Valid @RequestBody UpsertExerciseRequest req) {
        Exercise exercise = exerciseService.create(
                req.getName(),
                req.getDescription(),
                MuscleGroup.valueOf(req.getMuscleGroup().trim().toUpperCase()),
                ExerciseCategory.valueOf(req.getCategory().trim().toUpperCase()),
                ExerciseType.valueOf(req.getExerciseType().trim().toUpperCase()),
                Equipment.valueOf(req.getEquipment().trim().toUpperCase()),
                Difficulty.valueOf(req.getDifficulty().trim().toUpperCase()),
                MovementPattern.valueOf(req.getMovementPattern().trim().toUpperCase())
        );

        return map(exercise);
    }

    @AdminOnly
    @PutMapping("/{id}")
    public ExerciseResponse update(@PathVariable Long id, @Valid @RequestBody UpsertExerciseRequest req) {
        Exercise exercise = exerciseService.update(
                id,
                req.getName(),
                req.getDescription(),
                MuscleGroup.valueOf(req.getMuscleGroup().trim().toUpperCase()),
                ExerciseCategory.valueOf(req.getCategory().trim().toUpperCase()),
                ExerciseType.valueOf(req.getExerciseType().trim().toUpperCase()),
                Equipment.valueOf(req.getEquipment().trim().toUpperCase()),
                Difficulty.valueOf(req.getDifficulty().trim().toUpperCase()),
                MovementPattern.valueOf(req.getMovementPattern().trim().toUpperCase())
        );

        return map(exercise);
    }

    @AdminOnly
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        exerciseService.delete(id);
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