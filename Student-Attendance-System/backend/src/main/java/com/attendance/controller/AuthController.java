package com.attendance.controller;

import com.attendance.model.User;
import com.attendance.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("username and password required");
        }

        Optional<User> existing = userRepository.findByUsername(user.getUsername());
        if (existing.isPresent()) return ResponseEntity.badRequest().body("username already exists");

        user.setRole("ADMIN");
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("status","signup_success"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (username == null || password == null) return ResponseEntity.badRequest().body("username and password required");

        Optional<User> u = userRepository.findByUsernameAndPassword(username, password);
        if (u.isPresent()) return ResponseEntity.ok(Map.of("status","login_success"));
        return ResponseEntity.status(401).body("invalid credentials");
    }
}
