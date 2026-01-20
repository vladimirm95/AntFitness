package com.antfitness.ant.services;

import com.antfitness.ant.model.Role;
import com.antfitness.ant.model.User;
import com.antfitness.ant.repositories.UserRepository;
import com.antfitness.ant.requests.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername()))
            throw new IllegalArgumentException("Username already exists");
        if (userRepository.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email already exists");

        User u = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .role(Role.USER)
                .build();

        return userRepository.save(u);
    }

    public User getByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(String username, String email, String rawPassword, Role role) {
        if (userRepository.existsByUsername(username)) throw new IllegalArgumentException("Username already exists");
        if (userRepository.existsByEmail(email)) throw new IllegalArgumentException("Email already exists");

        User u = User.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordEncoder.encode(rawPassword))
                .role(role)
                .build();
        return userRepository.save(u);
    }

    public User updateUser(Long id, String username, String email, Role role) {
        User u = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!u.getUsername().equalsIgnoreCase(username) && userRepository.existsByUsername(username))
            throw new IllegalArgumentException("Username already exists");

        if (!u.getEmail().equalsIgnoreCase(email) && userRepository.existsByEmail(email))
            throw new IllegalArgumentException("Email already exists");

        u.setUsername(username);
        u.setEmail(email);
        u.setRole(role);
        return userRepository.save(u);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) throw new IllegalArgumentException("User not found");
        userRepository.deleteById(id);
    }
}
