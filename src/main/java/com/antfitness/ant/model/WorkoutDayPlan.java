package com.antfitness.ant.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "workout_day_plans",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "workout_date"})
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class WorkoutDayPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workout_date", nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean isCompleted = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @OneToMany(
            mappedBy = "workoutDayPlan",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<WorkoutExercise> exercises = new ArrayList<>();
}
