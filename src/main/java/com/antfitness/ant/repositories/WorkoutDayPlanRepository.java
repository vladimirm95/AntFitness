package com.antfitness.ant.repositories;

import com.antfitness.ant.model.WorkoutDayPlan;
import com.antfitness.ant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutDayPlanRepository extends JpaRepository<WorkoutDayPlan, Long> {

    Optional<WorkoutDayPlan> findByUserAndDate(User user, LocalDate date);
    List<WorkoutDayPlan> findAllByUserAndDateBetween(
            User user,
            LocalDate start,
            LocalDate end
    );
}
