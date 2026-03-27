package com.antfitness.ant.controllers;

import com.antfitness.ant.model.User;
import com.antfitness.ant.model.WorkoutDayPlan;
import com.antfitness.ant.requests.AddExerciseToWorkoutRequest;
import com.antfitness.ant.requests.CreateWorkoutPlanRequest;
import com.antfitness.ant.responses.WorkoutCalendarDayResponse;
import com.antfitness.ant.responses.WorkoutExerciseResponse;
import com.antfitness.ant.responses.WorkoutPlanResponse;
import com.antfitness.ant.services.UserService;
import com.antfitness.ant.services.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/workouts")
@CrossOrigin
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;
    private final UserService userService;

    @PostMapping
    public WorkoutPlanResponse create(
            @Valid @RequestBody CreateWorkoutPlanRequest request,
            Authentication auth
    ) {
        User user = userService.getByUsernameOrThrow(auth.getName());
        WorkoutDayPlan plan = workoutService.createPlan(user, request.getDate());
        return toPlanResponse(plan);
    }

    @GetMapping
    public WorkoutPlanResponse getByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Authentication auth
    ) {
        User user = userService.getByUsernameOrThrow(auth.getName());
        WorkoutDayPlan plan = workoutService.getByDate(user, date);
        return toPlanResponse(plan);
    }

    @PostMapping("/{id}/exercises")
    public WorkoutPlanResponse addExercise(
            @PathVariable Long id,
            @Valid @RequestBody AddExerciseToWorkoutRequest request
    ) {
        WorkoutDayPlan plan = workoutService.addExercise(
                id,
                request.getExerciseId(),
                request.getSets(),
                request.getReps()
        );
        return toPlanResponse(plan);
    }

    @DeleteMapping("/exercises/{workoutExerciseId}")
    public void deleteExercise(@PathVariable Long workoutExerciseId) {
        workoutService.deleteWorkoutExercise(workoutExerciseId);
    }

    @PutMapping("/{id}/complete")
    public WorkoutPlanResponse markCompleted(@PathVariable Long id) {
        WorkoutDayPlan plan = workoutService.markCompleted(id);
        return toPlanResponse(plan);
    }

    @GetMapping("/calendar")
    public List<WorkoutCalendarDayResponse> getCalendar(
            @RequestParam int year,
            @RequestParam int month,
            Authentication auth
    ) {
        User user = userService.getByUsernameOrThrow(auth.getName());
        return workoutService.getForMonth(user, year, month);
    }

    private WorkoutPlanResponse toPlanResponse(WorkoutDayPlan plan) {
        List<WorkoutExerciseResponse> exercises = plan.getExercises().stream()
                .map(we -> new WorkoutExerciseResponse(
                        we.getId(),
                        we.getExercise().getName(),
                        we.getSets(),
                        we.getReps(),
                        we.getOrderIndex()
                ))
                .toList();

        return new WorkoutPlanResponse(
                plan.getId(),
                plan.getDate(),
                plan.isCompleted(),
                exercises
        );
    }
}