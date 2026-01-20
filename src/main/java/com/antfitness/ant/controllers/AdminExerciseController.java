package com.antfitness.ant.controllers;

import com.antfitness.ant.model.MuscleGroup;
import com.antfitness.ant.model.Exercise;
import com.antfitness.ant.requests.UpsertExerciseRequest;
import com.antfitness.ant.responses.ExerciseResponse;
import com.antfitness.ant.security.aop.AdminOnly;
import com.antfitness.ant.services.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/exercises")
@CrossOrigin
@RequiredArgsConstructor
public class AdminExerciseController {

    private final ExerciseService exerciseService;

    @AdminOnly
    @PostMapping
    public ExerciseResponse create(@Valid @RequestBody UpsertExerciseRequest req) {
        MuscleGroup mg = MuscleGroup.valueOf(req.getMuscleGroup().trim().toUpperCase());
        Exercise e = exerciseService.create(req.getName(), req.getDescription(), mg);
        return new ExerciseResponse(e.getId(), e.getName(), e.getDescription(), e.getMuscleGroup().name());
    }

    @AdminOnly
    @PutMapping("/{id}")
    public ExerciseResponse update(@PathVariable Long id, @Valid @RequestBody UpsertExerciseRequest req) {
        MuscleGroup mg = MuscleGroup.valueOf(req.getMuscleGroup().trim().toUpperCase());
        Exercise e = exerciseService.update(id, req.getName(), req.getDescription(), mg);
        return new ExerciseResponse(e.getId(), e.getName(), e.getDescription(), e.getMuscleGroup().name());
    }

    @AdminOnly
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        exerciseService.delete(id);
    }
}
