# AntFitness API 

AntFitness is a Spring Boot REST API for fitness planning and workout tracking.
The project is designed as a backend for Android/Web applications.

##  Features

### Authentication & Authorization
- JWT-based authentication (signup / login)
- Role-based authorization (USER, ADMIN)
- Aspect-Oriented Programming (AOP) for admin-only actions
- Admin protection (cannot delete or downgrade last admin)

### Users
- View own profile (`/users/me`)
- Admin CRUD operations on users
- Role assignment during user creation

### Exercises
- List all exercises
- Filter exercises by muscle group
- Admin can create, update and delete exercises

### Workout Planning
- Create workout plan for a specific day (calendar-based)
- Add exercises to a workout day
- Mark workout day as completed
- One workout plan per user per date

---

##  Tech Stack
- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- Lombok
- Aspect-Oriented Programming (AOP)

---

##  Architecture
- Layered architecture:
  - Controller
  - Service
  - Repository
- DTOs for request/response
- Global exception handling
- Clear separation of authentication, authorization and business logic

---
## Security & Rate Limiting
- Login rate limiting using **Redis**
- Rate limiting based on **user + IP**
- Atomic counters with TTL to prevent brute-force attacks
- Implemented via custom Spring Security filters


