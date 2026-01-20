package com.antfitness.ant.config;

import com.antfitness.ant.model.Exercise;
import com.antfitness.ant.model.MuscleGroup;
import com.antfitness.ant.model.Role;
import com.antfitness.ant.model.User;
import com.antfitness.ant.repositories.ExerciseRepository;
import com.antfitness.ant.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExerciseRepository exerciseRepository;

    @Override
    public void run(String... args) {

        if (!userRepository.existsByEmail("vova@example.com")) {
            User admin = User.builder()
                    .username("Vova")
                    .email("vova@example.com")
                    .passwordHash(passwordEncoder.encode("123"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
        }

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
