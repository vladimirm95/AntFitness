package com.antfitness.ant.services;

import com.antfitness.ant.exceptions.ForbiddenException;
import com.antfitness.ant.model.Exercise;
import com.antfitness.ant.model.User;
import com.antfitness.ant.model.WorkoutDayPlan;
import com.antfitness.ant.model.WorkoutExercise;
import com.antfitness.ant.repositories.ExerciseRepository;
import com.antfitness.ant.repositories.WorkoutDayPlanRepository;
import com.antfitness.ant.repositories.WorkoutExerciseRepository;
import com.antfitness.ant.responses.WorkoutCalendarDayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutDayPlanRepository planRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final ExerciseRepository exerciseRepository;

    @CacheEvict(value = "workout_calendar_v2", allEntries = true)
    @Transactional
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

    @CacheEvict(value = "workout_calendar_v2", allEntries = true)
    @Transactional
    public WorkoutDayPlan addExercise(Long planId, Long exerciseId, int sets, int reps) {
        WorkoutDayPlan plan = getPlanOwnedByCurrentUserOrThrow(planId);

        if (plan.isCompleted()) {
            throw new IllegalArgumentException("Completed workout cannot be modified");
        }

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

    @CacheEvict(value = "workout_calendar_v2", allEntries = true)
    @Transactional
    public void deleteWorkoutExercise(Long workoutExerciseId) {
        WorkoutExercise we = workoutExerciseRepository.findById(workoutExerciseId)
                .orElseThrow(() -> new IllegalArgumentException("Workout exercise not found"));

        String currentUsername = currentUsername();

        if (!we.getWorkoutDayPlan().getUser().getUsername().equals(currentUsername)) {
            throw new ForbiddenException("You cannot delete exercises from another user's workout");
        }

        if (we.getWorkoutDayPlan().isCompleted()) {
            throw new IllegalArgumentException("Completed workout cannot be modified");
        }

        workoutExerciseRepository.delete(we);
    }

    @CacheEvict(value = "workout_calendar_v2", allEntries = true)
    @Transactional
    public WorkoutDayPlan markCompleted(Long id) {
        WorkoutDayPlan plan = getPlanOwnedByCurrentUserOrThrow(id);
        plan.setCompleted(true);
        return planRepository.save(plan);
    }

    public WorkoutDayPlan getByDate(User user, LocalDate date) {
        return planRepository.findByUserAndDate(user, date)
                .orElseThrow(() -> new IllegalArgumentException("Workout plan not found for this date"));
    }

    @Cacheable(value = "workout_calendar_v2", key = "#user.id + ':' + #year + ':' + #month")
    public List<WorkoutCalendarDayResponse> getForMonth(User user, int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        return planRepository.findAllByUserAndDateBetween(user, start, end)
                .stream()
                .map(plan -> new WorkoutCalendarDayResponse(
                        plan.getDate(),
                        plan.isCompleted()
                ))
                .toList();
    }

    public WorkoutDayPlan getById(Long id) {
        return getPlanOwnedByCurrentUserOrThrow(id);
    }

    private WorkoutDayPlan getPlanOwnedByCurrentUserOrThrow(Long planId) {
        WorkoutDayPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Workout plan not found"));

        String currentUsername = currentUsername();

        if (plan.getUser() == null || plan.getUser().getUsername() == null) {
            throw new IllegalStateException("Workout plan owner is missing");
        }

        if (!plan.getUser().getUsername().equals(currentUsername)) {
            throw new ForbiddenException("You cannot access another user's workout plan");
        }

        return plan;
    }

    private String currentUsername() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new ForbiddenException("Not authenticated");
        }
        return auth.getName();
    }
}