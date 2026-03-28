package com.antfitness.ant.services;

import com.antfitness.ant.model.*;
import com.antfitness.ant.repositories.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    public List<Exercise> findByMuscleGroup(MuscleGroup muscleGroup) {
        return exerciseRepository.findByMuscleGroup(muscleGroup);
    }

    public List<Exercise> findByCategory(ExerciseCategory category) {
        return exerciseRepository.findByCategory(category);
    }

    public List<Exercise> findByEquipment(Equipment equipment) {
        return exerciseRepository.findByEquipment(equipment);
    }

    public List<Exercise> findByDifficulty(Difficulty difficulty) {
        return exerciseRepository.findByDifficulty(difficulty);
    }

    public List<Exercise> findByExerciseType(ExerciseType exerciseType) {
        return exerciseRepository.findByExerciseType(exerciseType);
    }

    public Exercise getByIdOrThrow(Long id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));
    }

    public Exercise create(
            String name,
            String description,
            MuscleGroup muscleGroup,
            ExerciseCategory category,
            ExerciseType exerciseType,
            Equipment equipment,
            Difficulty difficulty,
            MovementPattern movementPattern
    ) {
        if (exerciseRepository.existsByNameIgnoreCase(name.trim())) {
            throw new IllegalArgumentException("Exercise with this name already exists");
        }

        Exercise exercise = Exercise.builder()
                .name(name.trim())
                .description(description != null ? description.trim() : null)
                .muscleGroup(muscleGroup)
                .category(category)
                .exerciseType(exerciseType)
                .equipment(equipment)
                .difficulty(difficulty)
                .movementPattern(movementPattern)
                .build();

        return exerciseRepository.save(exercise);
    }

    public Exercise update(
            Long id,
            String name,
            String description,
            MuscleGroup muscleGroup,
            ExerciseCategory category,
            ExerciseType exerciseType,
            Equipment equipment,
            Difficulty difficulty,
            MovementPattern movementPattern
    ) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));

        String trimmedName = name.trim();

        if (!exercise.getName().equalsIgnoreCase(trimmedName)
                && exerciseRepository.existsByNameIgnoreCase(trimmedName)) {
            throw new IllegalArgumentException("Exercise with this name already exists");
        }

        exercise.setName(trimmedName);
        exercise.setDescription(description != null ? description.trim() : null);
        exercise.setMuscleGroup(muscleGroup);
        exercise.setCategory(category);
        exercise.setExerciseType(exerciseType);
        exercise.setEquipment(equipment);
        exercise.setDifficulty(difficulty);
        exercise.setMovementPattern(movementPattern);

        return exerciseRepository.save(exercise);
    }

    public void delete(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));

        exerciseRepository.delete(exercise);
    }
}