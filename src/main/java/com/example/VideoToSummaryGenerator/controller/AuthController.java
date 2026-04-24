package com.example.VideoToSummaryGenerator.controller;

import com.example.VideoToSummaryGenerator.config.security.JwtUtil;
import com.example.VideoToSummaryGenerator.dto.LoginUserDTO;
import com.example.VideoToSummaryGenerator.dto.RegisterUserDTO;
import com.example.VideoToSummaryGenerator.entity.User;
import com.example.VideoToSummaryGenerator.entity.User.Role;
import com.example.VideoToSummaryGenerator.service.RegisterUserService;
import com.example.VideoToSummaryGenerator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final RegisterUserService registerUserService;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        User user = registerUserService.registerUser(registerUserDTO.getEmail(), registerUserDTO.getPassword());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword())
        );

        UserDetails userDetails = userService.loadUserByUsername(loginUserDTO.getEmail());

        Set<Role> roles = userDetails.getAuthorities().stream()
                .map(auth -> Role.valueOf(auth.getAuthority().replace("ROLE_", "")))
                .collect(Collectors.toSet());
        String token = jwtUtil.generateToken(
                userDetails.getUsername(),
                roles);

        return ResponseEntity.ok(token);

    }
}
