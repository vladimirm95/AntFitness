package com.antfitness.ant.controllers;

import com.antfitness.ant.model.User;
import com.antfitness.ant.responses.MeResponse;
import com.antfitness.ant.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public MeResponse me(Authentication auth) {
        // auth.getName() je username (subject iz tokena)
        User u = userService.getByUsernameOrThrow(auth.getName());
        return new MeResponse(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getRole().name()
        );
    }
}
