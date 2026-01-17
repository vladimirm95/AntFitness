package com.antfitness.ant.controllers;

import com.antfitness.ant.responses.MeResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/me")
    public MeResponse me(Authentication auth) {
        return new MeResponse(auth.getName());
    }
}
