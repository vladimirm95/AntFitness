package com.antfitness.ant.repositories;

import com.antfitness.ant.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    boolean existsByName(String name);
}
