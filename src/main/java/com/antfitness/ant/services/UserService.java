package com.antfitness.ant.services;


import com.antfitness.ant.model.Role;
import com.antfitness.ant.model.User;
import com.antfitness.ant.repositories.UserRepository;
import com.antfitness.ant.requests.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
