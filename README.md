# AntFitness API

AntFitness is a production-style Spring Boot REST API for workout planning and user management.  
The project is designed as a secure backend system for Android/Web applications, with a strong focus on clean architecture, security, and domain-driven design principles.

---

##  Overview

AntFitness provides:

- Secure user authentication and authorization
- Role-based access control (`USER`, `ADMIN`)
- Workout planning per calendar day
- Exercise management
- Protection against brute-force login attacks
- Clear separation between authentication, authorization, and business logic


---

##  Architecture


### Architectural Principles

- Controllers handle only HTTP-related concerns
- Services contain business logic and domain rules
- Repositories interact with the database via Spring Data JPA
- DTOs are used for request/response mapping (entities are never exposed)
- Global exception handling centralizes error responses
- Authentication and authorization are separated from business logic

This structure improves:

- Maintainability
- Scalability
- Testability
- Separation of concerns

---

## 🔐 Security Design

### Stateless JWT Authentication

- Users authenticate via `/auth/login`
- A signed JWT token is returned on successful authentication
- JWT is validated in a custom `JwtAuthenticationFilter`
- SecurityContext is populated for each request
- No server-side sessions are used

---

### Role-Based Authorization

- Roles: `USER`, `ADMIN`
- Access to protected endpoints is restricted based on roles
- Business rules are enforced at the service layer

---

### AOP-Based Admin Protection

A custom annotation `@AdminOnly` is implemented using Spring AOP.

#### Why AOP?

Instead of relying only on `@PreAuthorize`, a custom AOP approach was used to:

- Encapsulate admin validation logic in one place
- Avoid repeating security checks across controllers
- Keep controllers focused on HTTP concerns
- Demonstrate handling of cross-cutting concerns

---

### Redis-Based Login Rate Limiting

To protect the authentication endpoint from brute-force attacks, a Redis-based rate limiting mechanism was implemented.

#### How it works

- Each login attempt increments a Redis counter.
- Key format: `login:attempts:{username}:{ip}`
- An atomic `INCR` operation is used.
- A TTL defines the rate-limiting window (e.g. 60 seconds).
- If the threshold is exceeded → HTTP 429 (Too Many Requests) is returned.

#### Why Redis?

- In-memory performance
- Atomic counter operations
- Built-in TTL support
- Production-ready distributed rate limiting capability

---

## 🏋️ Workout Planning Domain

The application enforces domain rules at the service layer:

- One workout plan per user per date
- Users can access only their own workout plans
- Only admins can manage other users
- The last admin cannot be deleted or downgraded

### Workout Features

- Create workout plan for a specific date
- Add exercises to a workout day
- Mark workout day as completed
- Filter exercises by muscle group

---

##  Database

- PostgreSQL
- Spring Data JPA
- Flyway for versioned migrations

Flyway ensures:

- Version-controlled schema evolution
- Reproducible database state
- Production-style migration workflow

---

##  Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Redis
- Flyway
- Lombok
- Aspect-Oriented Programming (AOP)

---

##  Engineering Focus

AntFitness was built to demonstrate:

- Security-focused backend development
- Clean separation of concerns
- Domain rule enforcement
- Production-oriented architectural patterns
- System-level thinking beyond simple CRUD applications


