package com.antfitness.ant.config;

import com.antfitness.ant.model.Exercise;
import com.antfitness.ant.model.MuscleGroup;
import com.antfitness.ant.repositories.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ExerciseRepository exerciseRepository;

    @Override
    public void run(String... args) {
        if (exerciseRepository.count() > 0) return;

        exerciseRepository.save(Exercise.builder()
                .name("Push-up")
                .description("Classic bodyweight chest exercise.")
                .muscleGroup(MuscleGroup.CHEST)
                .build());

        exerciseRepository.save(Exercise.builder()
                .name("Pull-up")
                .description("Great for back and arms.")
                .muscleGroup(MuscleGroup.BACK)
                .build());

        exerciseRepository.save(Exercise.builder()
                .name("Squat")
                .description("Leg strength exercise.")
                .muscleGroup(MuscleGroup.LEGS)
                .build());
    }
}
