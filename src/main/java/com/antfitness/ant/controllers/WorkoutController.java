package com.antfitness.ant.controllers;

import com.antfitness.ant.model.User;
import com.antfitness.ant.responses.*;
import com.antfitness.ant.requests.*;
import com.antfitness.ant.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{id}")
    public WorkoutPlanResponse getById(@PathVariable Long id) {
        return map(workoutService.getById(id));
    }

    @PostMapping
    public WorkoutPlanResponse create(
            @Valid @RequestBody CreateWorkoutPlanRequest req,
            Authentication auth
    ) {
        User user = userService.getByUsernameOrThrow(auth.getName());
        var plan = workoutService.createPlan(user, req.getDate());
        return map(plan);
    }

    @DeleteMapping("/exercises/{id}")
    public void deleteWorkoutExercise(@PathVariable Long id) {
        workoutService.deleteWorkoutExercise(id);
    }

    @PostMapping("/{id}/exercises")
    public WorkoutPlanResponse addExercise(
            @PathVariable Long id,
            @Valid @RequestBody AddExerciseToWorkoutRequest req
    ) {
        var plan = workoutService.addExercise(id, req.getExerciseId(), req.getSets(), req.getReps());
        return map(plan);
    }

    @PutMapping("/{id}/complete")
    public WorkoutPlanResponse complete(@PathVariable Long id) {
        return map(workoutService.markCompleted(id));
    }

    private WorkoutPlanResponse map(com.antfitness.ant.model.WorkoutDayPlan plan) {
        return new WorkoutPlanResponse(
                plan.getId(),
                plan.getDate(),
                plan.isCompleted(),
                plan.getExercises().stream()
                        .map(e -> new WorkoutExerciseResponse(
                                e.getId(),
                                e.getExercise().getName(),
                                e.getSets(),
                                e.getReps(),
                                e.getOrderIndex()
                        ))
                        .toList()
        );
    }
    @GetMapping
    public WorkoutPlanResponse getByDate(
            @RequestParam String date,
            Authentication auth
    ) {
        User user = userService.getByUsernameOrThrow(auth.getName());
        var plan = workoutService.getByDate(user, LocalDate.parse(date));
        return map(plan);
    }

    @GetMapping("/calendar")
    public List<WorkoutCalendarDayResponse> calendar(
            @RequestParam int year,
            @RequestParam int month,
            Authentication auth
    ) {
        User user = userService.getByUsernameOrThrow(auth.getName());

        return workoutService.getForMonth(user, year, month).stream()
                .map(p -> new WorkoutCalendarDayResponse(
                        p.getDate(),
                        p.isCompleted()
                ))
                .toList();
    }


}
