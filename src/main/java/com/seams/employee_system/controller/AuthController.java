package com.seams.employee_system.controller;

import com.seams.employee_system.entity.User;
import com.seams.employee_system.exception.InvalidCredentialsException;
import com.seams.employee_system.repository.UserRepository;
import com.seams.employee_system.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder; // ✅ IMPORTANT

    public AuthController(UserRepository userRepository,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // AuthController.java

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {

        User dbUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username"));

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        String token = jwtUtil.generateToken(
                dbUser.getUsername(),
                dbUser.getRole().name()
        );

        Map<String, String> response = new java.util.HashMap<>();
        response.put("token", token);
        response.put("role", dbUser.getRole().name());

        if (dbUser.getEmployee() != null) {
            response.put("employeeId", String.valueOf(dbUser.getEmployee().getId()));
        }

        return response;
    }

}
