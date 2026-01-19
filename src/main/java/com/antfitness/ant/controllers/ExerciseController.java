package com.antfitness.ant.controllers;

import com.antfitness.ant.model.Exercise;
import com.antfitness.ant.model.MuscleGroup;
import com.antfitness.ant.responses.ExerciseResponse;
import com.antfitness.ant.services.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
@CrossOrigin
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    public List<ExerciseResponse> all(@RequestParam(required = false) String muscleGroup) {

        List<Exercise> exercises;

        if (muscleGroup == null || muscleGroup.isBlank()) {
            exercises = exerciseService.findAll();
        } else {
            // string to enum
            var mg = MuscleGroup.valueOf(muscleGroup.trim().toUpperCase());
            exercises = exerciseService.findByMuscleGroup(mg);
        }

        return exercises.stream()
                .map(this::toResponse)
                .toList();
    }


    private ExerciseResponse toResponse(Exercise e) {
        return new ExerciseResponse(
                e.getId(),
                e.getName(),
                e.getDescription(),
                e.getMuscleGroup().name()
        );
    }
}
