package com.example.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping
    public String welcome() {
        return "Welcome to this Spring Boot Web Security app";
    }
    @GetMapping("/users")
    //@PreAuthorize("hasAnyRole('ADMINS','USERS')")
    public String users() {
        return "Authorized user";
    }
    @GetMapping("/admins")
    //@PreAuthorize("hasRole('ADMINS')")
    public String admins() {
        return "Authorized admin";
    }
}
