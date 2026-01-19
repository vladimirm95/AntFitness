package com.antfitness.ant.repositories;

import com.antfitness.ant.model.Exercise;
import com.antfitness.ant.model.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    boolean existsByName(String name);
    List<Exercise> findAllByMuscleGroup(MuscleGroup muscleGroup);
}
