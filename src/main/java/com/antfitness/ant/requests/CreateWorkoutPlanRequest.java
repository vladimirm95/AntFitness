package com.antfitness.ant.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CreateWorkoutPlanRequest {

    @NotNull
    private LocalDate date;
}
