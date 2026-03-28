ALTER TABLE exercises
    ADD COLUMN category varchar(30),
    ADD COLUMN exercise_type varchar(30),
    ADD COLUMN equipment varchar(30),
    ADD COLUMN difficulty varchar(30),
    ADD COLUMN movement_pattern varchar(30);

UPDATE exercises
SET
    category = 'STRENGTH',
    exercise_type = CASE
                        WHEN muscle_group IN ('CHEST', 'BACK', 'LEGS', 'FULL_BODY') THEN 'COMPOUND'
                        ELSE 'ISOLATION'
        END,
    equipment = CASE
                    WHEN name IN ('Push-up', 'Pull-up') THEN 'BODYWEIGHT'
                    WHEN name = 'Squat' THEN 'BODYWEIGHT'
                    ELSE 'BODYWEIGHT'
        END,
    difficulty = CASE
                     WHEN name = 'Pull-up' THEN 'INTERMEDIATE'
                     ELSE 'BEGINNER'
        END,
    movement_pattern = CASE
                           WHEN muscle_group = 'CHEST' THEN 'PUSH'
                           WHEN muscle_group = 'BACK' THEN 'PULL'
                           WHEN muscle_group = 'LEGS' THEN 'SQUAT'
                           WHEN muscle_group = 'CORE' THEN 'CORE'
                           ELSE 'FULL_BODY'
        END;

ALTER TABLE exercises
    ALTER COLUMN category SET NOT NULL,
ALTER COLUMN exercise_type SET NOT NULL,
    ALTER COLUMN equipment SET NOT NULL,
    ALTER COLUMN difficulty SET NOT NULL,
    ALTER COLUMN movement_pattern SET NOT NULL;