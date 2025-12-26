package com.krit.leave_planner_backend.controller;

import com.krit.leave_planner_backend.dto.LoginRequest;
import com.krit.leave_planner_backend.dto.LoginResponse;
import com.krit.leave_planner_backend.entity.User;
import com.krit.leave_planner_backend.service.JwtService;
import com.krit.leave_planner_backend.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public AuthController(UserService userService,
                          JwtService jwtService,
                          PasswordEncoder encoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {

        User user = userService.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(
                token,
                user.getId(),
                user.getName(),
                user.getRole(),
                user.getManagerAccess()
        );
    }
}
