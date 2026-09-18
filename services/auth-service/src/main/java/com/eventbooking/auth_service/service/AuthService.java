package com.eventbooking.auth_service.service;

import com.eventbooking.auth_service.dto.*;
import com.eventbooking.auth_service.model.User;
import com.eventbooking.auth_service.repository.UserRepository;
import com.eventbooking.auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor 
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email ya está registrado");
        }

        User user = User.builder() 
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Password encriptada con BCrypt
                .role("USER")
                .build();

        userRepository.save(user);

        // Simulamos la devolución de token
        String token = jwtService.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");                
        }

        // Simulamos la devolución de token
        String token = jwtService.generateToken(user.getEmail(), user.getRole()); 

        return new AuthResponse(token);
    }
}