package com.dsytnykov.controller;

import com.dsytnykov.dto.LoginRequest;
import com.dsytnykov.dto.RegisterRequest;
import com.dsytnykov.repository.UserRepository;
import com.dsytnykov.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtUtils;

    @PostMapping("/login")
    public String authenticateUser(@RequestBody LoginRequest user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtils.generateJwtToken(authentication);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest register) {
        if (userRepository.existsByUsername(register.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(register.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        com.dsytnykov.model.User user = com.dsytnykov.model.User.builder()
                .username(register.getUsername())
                .email(register.getEmail())
                .password(encoder.encode(register.getPassword()))
                .build();
        Set<String> strRoles = register.getRoles();
        Set<String> roles = new HashSet<>();

        if (strRoles == null) {
            roles.add("ROLE_USER");
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    roles.add("ROLE_ADMIN");
                } else {
                    roles.add("ROLE_USER");
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
