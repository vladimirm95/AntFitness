AntFitness API

AntFitness is a production-style Spring Boot REST API for workout planning and user management.
The project is designed as a secure backend system for Android/Web applications, with a strong focus on architecture, security, and clean separation of concerns.

 Features
 Authentication & Authorization

Stateless JWT-based authentication (signup / login)

Role-based authorization (USER, ADMIN)

Custom @AdminOnly annotation implemented via AOP

Admin protection (cannot delete or downgrade the last admin)

Login rate limiting using Redis (brute-force protection)

 User Management

View own profile (/users/me)

Admin CRUD operations on users

Role assignment during user creation

last_login tracking on successful authentication

 Workout Planning

Create workout plan for a specific date (calendar-based)

One workout plan per user per date (domain constraint)

Add exercises to a workout day

Mark workout day as completed

Filter exercises by muscle group

 Architecture

Key architectural decisions:

Controllers handle HTTP concerns only

Services contain business logic and domain rules

Repositories interact with the database via Spring Data JPA

DTOs are used for request/response mapping (entities are not exposed)

Global exception handling centralizes error responses

This ensures:

Clear separation of concerns

Maintainability

Testability

Scalability

 Security Design
1. Stateless JWT Authentication

Users authenticate via /auth/login

On success, a JWT is issued

JWT is validated in a custom JwtAuthenticationFilter

Security context is populated per request

No server-side session storage

This approach ensures scalability and horizontal compatibility.

2. Redis-Based Login Rate Limiting

To prevent brute-force login attacks:

Each login attempt increments a Redis counter

Key format: login:attempts:{username}:{ip}

Atomic increment (INCR)

TTL-based sliding window (e.g. 60 seconds)

If threshold is exceeded → HTTP 429 (Too Many Requests)

Why Redis?

Fast in-memory store

Atomic operations

Built-in TTL support

Production-ready rate limiting mechanism

3. AOP-Based Admin Protection

Instead of relying solely on @PreAuthorize, a custom annotation:

@AdminOnly

is implemented using Spring AOP.

Why?

Keeps security logic separate from controllers

Encapsulates admin validation rules

Prevents accidental misuse of privileged endpoints

Demonstrates cross-cutting concern handling

 Database

PostgreSQL

Spring Data JPA

Flyway for versioned migrations

Flyway ensures:

Reproducible schema evolution

Version control for database changes

Production-style migration flow

 Tech Stack

Java 17

Spring Boot

Spring Security

Spring Data JPA

PostgreSQL

Redis

Flyway

Lombok

Aspect-Oriented Programming (AOP)

 Domain Constraints

The project enforces business rules at the service layer:

A user can have only one workout plan per date

Only admins can modify other users

The last admin cannot be removed

Users can only access their own workout plans

This prevents logic leakage into controllers and ensures consistency.
