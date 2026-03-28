package com.antfitness.ant.config;

import com.antfitness.ant.model.*;
import com.antfitness.ant.repositories.ExerciseRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder {

    private final ExerciseRepository exerciseRepository;

    @PostConstruct
    public void seedExercises() {
        seed(
                "Push-up",
                "Classic bodyweight pushing exercise for chest, shoulders, and triceps.",
                MuscleGroup.CHEST,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.BODYWEIGHT,
                Difficulty.BEGINNER,
                MovementPattern.PUSH
        );

        seed(
                "Pull-up",
                "Bodyweight vertical pulling movement focused on back and arms.",
                MuscleGroup.BACK,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.BODYWEIGHT,
                Difficulty.INTERMEDIATE,
                MovementPattern.PULL
        );

        seed(
                "Squat",
                "Fundamental lower-body exercise targeting legs and glutes.",
                MuscleGroup.LEGS,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.BODYWEIGHT,
                Difficulty.BEGINNER,
                MovementPattern.SQUAT
        );

        seed(
                "Bench Press",
                "Classic horizontal barbell pressing exercise for chest strength.",
                MuscleGroup.CHEST,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.BARBELL,
                Difficulty.INTERMEDIATE,
                MovementPattern.PUSH
        );

        seed(
                "Incline Dumbbell Press",
                "Upper chest pressing movement with dumbbells.",
                MuscleGroup.CHEST,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.DUMBBELL,
                Difficulty.INTERMEDIATE,
                MovementPattern.PUSH
        );

        seed(
                "Chest Fly",
                "Isolation movement for stretching and contracting the chest.",
                MuscleGroup.CHEST,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.DUMBBELL,
                Difficulty.BEGINNER,
                MovementPattern.PUSH
        );

        seed(
                "Cable Crossover",
                "Cable-based chest isolation exercise with constant tension.",
                MuscleGroup.CHEST,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.CABLE,
                Difficulty.INTERMEDIATE,
                MovementPattern.PUSH
        );

        seed(
                "Barbell Row",
                "Compound rowing exercise for back thickness and pulling strength.",
                MuscleGroup.BACK,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.BARBELL,
                Difficulty.INTERMEDIATE,
                MovementPattern.PULL
        );

        seed(
                "Lat Pulldown",
                "Cable pulling exercise targeting the lats and upper back.",
                MuscleGroup.BACK,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.CABLE,
                Difficulty.BEGINNER,
                MovementPattern.PULL
        );

        seed(
                "Seated Cable Row",
                "Horizontal cable rowing exercise for mid-back development.",
                MuscleGroup.BACK,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.CABLE,
                Difficulty.BEGINNER,
                MovementPattern.PULL
        );

        seed(
                "Face Pull",
                "Rear delt and upper back exercise using a cable.",
                MuscleGroup.BACK,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.CABLE,
                Difficulty.BEGINNER,
                MovementPattern.PULL
        );

        seed(
                "Deadlift",
                "Heavy full-body posterior chain exercise with barbell.",
                MuscleGroup.FULL_BODY,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.BARBELL,
                Difficulty.ADVANCED,
                MovementPattern.HINGE
        );

        seed(
                "Romanian Deadlift",
                "Hip hinge exercise focused on hamstrings and glutes.",
                MuscleGroup.LEGS,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.BARBELL,
                Difficulty.INTERMEDIATE,
                MovementPattern.HINGE
        );

        seed(
                "Leg Press",
                "Machine-based lower body pushing exercise for quads and glutes.",
                MuscleGroup.LEGS,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.MACHINE,
                Difficulty.BEGINNER,
                MovementPattern.SQUAT
        );

        seed(
                "Walking Lunges",
                "Dynamic unilateral leg movement for balance and strength.",
                MuscleGroup.LEGS,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.DUMBBELL,
                Difficulty.INTERMEDIATE,
                MovementPattern.LUNGE
        );

        seed(
                "Bulgarian Split Squat",
                "Single-leg lower body exercise challenging balance and strength.",
                MuscleGroup.LEGS,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.DUMBBELL,
                Difficulty.INTERMEDIATE,
                MovementPattern.LUNGE
        );

        seed(
                "Leg Extension",
                "Machine isolation exercise for the quadriceps.",
                MuscleGroup.LEGS,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.MACHINE,
                Difficulty.BEGINNER,
                MovementPattern.SQUAT
        );

        seed(
                "Leg Curl",
                "Isolation exercise targeting the hamstrings.",
                MuscleGroup.LEGS,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.MACHINE,
                Difficulty.BEGINNER,
                MovementPattern.HINGE
        );

        seed(
                "Calf Raise",
                "Lower leg isolation exercise for calf development.",
                MuscleGroup.LEGS,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.MACHINE,
                Difficulty.BEGINNER,
                MovementPattern.CARRY
        );

        seed(
                "Shoulder Press",
                "Overhead pressing movement for shoulders and triceps.",
                MuscleGroup.SHOULDERS,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.DUMBBELL,
                Difficulty.INTERMEDIATE,
                MovementPattern.PUSH
        );

        seed(
                "Lateral Raise",
                "Isolation exercise targeting the side delts.",
                MuscleGroup.SHOULDERS,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.DUMBBELL,
                Difficulty.BEGINNER,
                MovementPattern.PUSH
        );

        seed(
                "Front Raise",
                "Anterior delt isolation movement with dumbbells or plates.",
                MuscleGroup.SHOULDERS,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.DUMBBELL,
                Difficulty.BEGINNER,
                MovementPattern.PUSH
        );

        seed(
                "Rear Delt Fly",
                "Isolation exercise for rear shoulders and posture support.",
                MuscleGroup.SHOULDERS,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.DUMBBELL,
                Difficulty.BEGINNER,
                MovementPattern.PULL
        );

        seed(
                "Bicep Curl",
                "Isolation exercise for the biceps.",
                MuscleGroup.ARMS,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.DUMBBELL,
                Difficulty.BEGINNER,
                MovementPattern.PULL
        );

        seed(
                "Hammer Curl",
                "Neutral-grip curl variation targeting biceps and forearms.",
                MuscleGroup.ARMS,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.DUMBBELL,
                Difficulty.BEGINNER,
                MovementPattern.PULL
        );

        seed(
                "Preacher Curl",
                "Strict biceps isolation exercise using a bench setup.",
                MuscleGroup.ARMS,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.MACHINE,
                Difficulty.INTERMEDIATE,
                MovementPattern.PULL
        );

        seed(
                "Tricep Pushdown",
                "Cable isolation movement for the triceps.",
                MuscleGroup.ARMS,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.CABLE,
                Difficulty.BEGINNER,
                MovementPattern.PUSH
        );

        seed(
                "Overhead Tricep Extension",
                "Isolation exercise targeting the long head of the triceps.",
                MuscleGroup.ARMS,
                ExerciseCategory.STRENGTH,
                ExerciseType.ISOLATION,
                Equipment.DUMBBELL,
                Difficulty.BEGINNER,
                MovementPattern.PUSH
        );

        seed(
                "Dips",
                "Bodyweight pushing exercise for chest, shoulders, and triceps.",
                MuscleGroup.CHEST,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.BODYWEIGHT,
                Difficulty.INTERMEDIATE,
                MovementPattern.PUSH
        );

        seed(
                "Plank",
                "Core stability exercise performed isometrically.",
                MuscleGroup.CORE,
                ExerciseCategory.CORE,
                ExerciseType.ISOLATION,
                Equipment.BODYWEIGHT,
                Difficulty.BEGINNER,
                MovementPattern.CORE
        );

        seed(
                "Side Plank",
                "Core and oblique stability exercise.",
                MuscleGroup.CORE,
                ExerciseCategory.CORE,
                ExerciseType.ISOLATION,
                Equipment.BODYWEIGHT,
                Difficulty.BEGINNER,
                MovementPattern.CORE
        );

        seed(
                "Russian Twist",
                "Rotational core exercise for obliques and trunk control.",
                MuscleGroup.CORE,
                ExerciseCategory.CORE,
                ExerciseType.ISOLATION,
                Equipment.BODYWEIGHT,
                Difficulty.BEGINNER,
                MovementPattern.CORE
        );

        seed(
                "Mountain Climbers",
                "Dynamic bodyweight exercise combining core and cardio demand.",
                MuscleGroup.CORE,
                ExerciseCategory.CARDIO,
                ExerciseType.COMPOUND,
                Equipment.BODYWEIGHT,
                Difficulty.INTERMEDIATE,
                MovementPattern.FULL_BODY
        );

        seed(
                "Burpees",
                "High-demand full body conditioning movement.",
                MuscleGroup.FULL_BODY,
                ExerciseCategory.CARDIO,
                ExerciseType.COMPOUND,
                Equipment.BODYWEIGHT,
                Difficulty.INTERMEDIATE,
                MovementPattern.FULL_BODY
        );

        seed(
                "Jump Rope",
                "Simple cardio exercise for conditioning and coordination.",
                MuscleGroup.FULL_BODY,
                ExerciseCategory.CARDIO,
                ExerciseType.COMPOUND,
                Equipment.BODYWEIGHT,
                Difficulty.BEGINNER,
                MovementPattern.FULL_BODY
        );

        seed(
                "Treadmill Run",
                "Steady-state cardio exercise for endurance and conditioning.",
                MuscleGroup.FULL_BODY,
                ExerciseCategory.CARDIO,
                ExerciseType.COMPOUND,
                Equipment.MACHINE,
                Difficulty.BEGINNER,
                MovementPattern.FULL_BODY
        );

        seed(
                "Cycling",
                "Low-impact cardio activity for endurance and leg conditioning.",
                MuscleGroup.LEGS,
                ExerciseCategory.CARDIO,
                ExerciseType.COMPOUND,
                Equipment.MACHINE,
                Difficulty.BEGINNER,
                MovementPattern.FULL_BODY
        );

        seed(
                "Band Pull-Apart",
                "Band exercise for upper back activation and posture.",
                MuscleGroup.BACK,
                ExerciseCategory.MOBILITY,
                ExerciseType.ISOLATION,
                Equipment.BAND,
                Difficulty.BEGINNER,
                MovementPattern.PULL
        );

        seed(
                "Cat-Cow Stretch",
                "Spinal mobility drill for warm-up and recovery.",
                MuscleGroup.CORE,
                ExerciseCategory.MOBILITY,
                ExerciseType.ISOLATION,
                Equipment.BODYWEIGHT,
                Difficulty.BEGINNER,
                MovementPattern.CORE
        );

        seed(
                "Hip Flexor Stretch",
                "Mobility drill for hips and lower body flexibility.",
                MuscleGroup.LEGS,
                ExerciseCategory.MOBILITY,
                ExerciseType.ISOLATION,
                Equipment.BODYWEIGHT,
                Difficulty.BEGINNER,
                MovementPattern.LUNGE
        );

        seed(
                "Farmer Carry",
                "Loaded carry exercise for grip, core, and total body stability.",
                MuscleGroup.FULL_BODY,
                ExerciseCategory.STRENGTH,
                ExerciseType.COMPOUND,
                Equipment.DUMBBELL,
                Difficulty.INTERMEDIATE,
                MovementPattern.CARRY
        );
    }

    private void seed(
            String name,
            String description,
            MuscleGroup muscleGroup,
            ExerciseCategory category,
            ExerciseType exerciseType,
            Equipment equipment,
            Difficulty difficulty,
            MovementPattern movementPattern
    ) {
        if (exerciseRepository.existsByNameIgnoreCase(name)) {
            return;
        }

        exerciseRepository.save(
                Exercise.builder()
                        .name(name)
                        .description(description)
                        .muscleGroup(muscleGroup)
                        .category(category)
                        .exerciseType(exerciseType)
                        .equipment(equipment)
                        .difficulty(difficulty)
                        .movementPattern(movementPattern)
                        .build()
        );
    }
}