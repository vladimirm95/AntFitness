create table users (
                       id bigserial primary key,
                       username varchar(50) not null unique,
                       email varchar(120) not null unique,
                       password_hash varchar(255) not null,
                       role varchar(20) not null
);

create table exercises (
                           id bigserial primary key,
                           name varchar(80) not null unique,
                           description varchar(500),
                           muscle_group varchar(30) not null
);

create table workout_day_plans (
                                   id bigserial primary key,
                                   workout_date date not null,
                                   is_completed boolean not null default false,
                                   user_id bigint not null,
                                   constraint fk_workout_plan_user
                                       foreign key (user_id)
                                           references users(id)
                                           on delete cascade,
                                   constraint uq_user_date unique (user_id, workout_date)
);

create table workout_exercises (
                                   id bigserial primary key,
                                   workout_day_plan_id bigint not null,
                                   exercise_id bigint not null,
                                   sets int not null,
                                   reps int not null,
                                   order_index int not null,
                                   constraint fk_we_plan
                                       foreign key (workout_day_plan_id)
                                           references workout_day_plans(id)
                                           on delete cascade,
                                   constraint fk_we_exercise
                                       foreign key (exercise_id)
                                           references exercises(id)
);

create index idx_plans_user_date
    on workout_day_plans(user_id, workout_date);

create index idx_we_plan
    on workout_exercises(workout_day_plan_id);

create index idx_we_exercise
    on workout_exercises(exercise_id);