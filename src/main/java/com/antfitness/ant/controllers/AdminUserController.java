package com.antfitness.ant.controllers;

import com.antfitness.ant.model.Role;
import com.antfitness.ant.model.User;
import com.antfitness.ant.requests.CreateUserRequest;
import com.antfitness.ant.responses.UserResponse;
import com.antfitness.ant.security.aop.AdminOnly;
import com.antfitness.ant.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@CrossOrigin
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @AdminOnly
    @GetMapping
    public List<UserResponse> all() {
        return userService.findAllUsers().stream()
                .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRole().name()))
                .toList();
    }

    @AdminOnly
    @PostMapping
    public UserResponse create(@Valid @RequestBody CreateUserRequest req) {
        Role role = Role.valueOf(req.getRole().trim().toUpperCase());
        User u = userService.createUser(req.getUsername(), req.getEmail(), req.getPassword(), role);
        return new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRole().name());
    }

    @AdminOnly
    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody CreateUserRequest req) {
        Role role = Role.valueOf(req.getRole().trim().toUpperCase());
        User u = userService.updateUser(id, req.getUsername(), req.getEmail(), role);
        return new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRole().name());
    }

    @AdminOnly
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
