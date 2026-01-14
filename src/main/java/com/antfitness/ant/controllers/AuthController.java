package com.antfitness.ant.controllers;


import com.antfitness.ant.requests.LoginRequest;
import com.antfitness.ant.requests.RegisterRequest;
import com.antfitness.ant.responses.AuthResponse;
import com.antfitness.ant.responses.MeResponse;
import com.antfitness.ant.security.JwtUtil;
import com.antfitness.ant.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody RegisterRequest req) {
        userService.register(req);
        String token = jwtUtil.generateToken(req.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsernameOrEmail(), req.getPassword())
        );

        String username = auth.getName();
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(new AuthResponse(token));
    }
    @GetMapping("/me")
    public MeResponse me(org.springframework.security.core.Authentication auth) {
        return new MeResponse(auth.getName());
    }

}
