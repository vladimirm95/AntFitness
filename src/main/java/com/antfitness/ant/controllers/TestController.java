package com.antfitness.ant.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/me")
    public String me() {
        return "OK - authenticated";
    }
}
