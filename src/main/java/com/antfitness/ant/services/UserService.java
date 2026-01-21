package com.antfitness.ant.services;

import com.antfitness.ant.model.Role;
import com.antfitness.ant.model.User;
import com.antfitness.ant.repositories.UserRepository;
import com.antfitness.ant.requests.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public User updateUser(Long id, String username, String email, Role newRole) {

        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String currentUsername =
                SecurityContextHolder.getContext().getAuthentication().getName();

        //  Admin ne moze menjati samog sebe
        if (userToUpdate.getUsername().equals(currentUsername)) {
            throw new IllegalArgumentException("Admin cannot update his own role");
        }

        if (userToUpdate.getRole() == Role.ADMIN && newRole == Role.USER) {

            long adminCount = userRepository.findAll().stream()
                    .filter(u -> u.getRole() == Role.ADMIN)
                    .count();

            if (adminCount <= 1) {
                throw new IllegalArgumentException("Cannot downgrade the last admin");
            }
        }

        if (!userToUpdate.getUsername().equalsIgnoreCase(username)
                && userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (!userToUpdate.getEmail().equalsIgnoreCase(email)
                && userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        userToUpdate.setUsername(username);
        userToUpdate.setEmail(email);
        userToUpdate.setRole(newRole);

        return userRepository.save(userToUpdate);
    }



    public void deleteUser(Long id) {

        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // username trenutno ulogovanog korisnika
        String currentUsername =
                SecurityContextHolder.getContext().getAuthentication().getName();

        if (userToDelete.getUsername().equals(currentUsername)) {
            throw new IllegalArgumentException("Admin cannot delete himself");
        }

        // ne moze se obrisati poslednji admin
        if (userToDelete.getRole() == Role.ADMIN) {
            long adminCount = userRepository.findAll().stream()
                    .filter(u -> u.getRole() == Role.ADMIN)
                    .count();

            if (adminCount <= 1) {
                throw new IllegalArgumentException("Cannot delete the last admin");
            }
        }
    }
}
