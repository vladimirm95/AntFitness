package com.antfitness.ant.services;

import com.antfitness.ant.model.Exercise;
import com.antfitness.ant.model.MuscleGroup;
import com.antfitness.ant.repositories.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Cacheable("exercises_all")
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    @Cacheable(value = "exercises_by_group", key = "#muscleGroup")
    public List<Exercise> findByMuscleGroup(MuscleGroup muscleGroup) {
        return exerciseRepository.findAllByMuscleGroup(muscleGroup);
    }

    @CacheEvict(value = {"exercises_all", "exercises_by_group"}, allEntries = true)
    public Exercise create(String name, String description, MuscleGroup mg) {
        if (exerciseRepository.existsByName(name)) {
            throw new IllegalArgumentException("Exercise with this name already exists");
        }
        Exercise e = Exercise.builder()
                .name(name)
                .description(description)
                .muscleGroup(mg)
                .build();
        return exerciseRepository.save(e);
    }
    @CacheEvict(value = {"exercises_all", "exercises_by_group"}, allEntries = true)
    public Exercise update(Long id, String name, String description, MuscleGroup mg) {
        Exercise e = exerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));

        if (!e.getName().equalsIgnoreCase(name) && exerciseRepository.existsByName(name)) {
            throw new IllegalArgumentException("Exercise with this name already exists");
        }

        e.setName(name);
        e.setDescription(description);
        e.setMuscleGroup(mg);
        return exerciseRepository.save(e);
    }
    @CacheEvict(value = {"exercises_all", "exercises_by_group"}, allEntries = true)
    public void delete(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new IllegalArgumentException("Exercise not found");
        }
        exerciseRepository.deleteById(id);
    }
}
