package com.antfitness.ant.repositories;

import com.antfitness.ant.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findByMuscleGroup(MuscleGroup muscleGroup);

    List<Exercise> findByCategory(ExerciseCategory category);

    List<Exercise> findByEquipment(Equipment equipment);

    List<Exercise> findByDifficulty(Difficulty difficulty);

    List<Exercise> findByExerciseType(ExerciseType exerciseType);

    boolean existsByNameIgnoreCase(String name);
}