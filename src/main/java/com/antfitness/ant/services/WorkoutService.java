package com.antfitness.ant.services;

import com.antfitness.ant.model.*;
import com.antfitness.ant.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutDayPlanRepository planRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutDayPlan createPlan(User user, LocalDate date) {
        planRepository.findByUserAndDate(user, date)
                .ifPresent(p -> {
                    throw new IllegalArgumentException("Workout plan already exists for this date");
                });

        WorkoutDayPlan plan = WorkoutDayPlan.builder()
                .user(user)
                .date(date)
                .isCompleted(false)
                .build();

        return planRepository.save(plan);
    }

    public WorkoutDayPlan addExercise(Long planId, Long exerciseId, int sets, int reps) {

        WorkoutDayPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Workout plan not found"));

        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));

        int orderIndex = plan.getExercises().size() + 1;

        WorkoutExercise we = WorkoutExercise.builder()
                .workoutDayPlan(plan)
                .exercise(exercise)
                .sets(sets)
                .reps(reps)
                .orderIndex(orderIndex)
                .build();

        plan.getExercises().add(we);
        return planRepository.save(plan);
    }

    public WorkoutDayPlan markCompleted(Long id) {
        WorkoutDayPlan plan = planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workout plan not found"));

        plan.setCompleted(true);
        return planRepository.save(plan);
    }
}
