package com.eventbooking.auth_service.service;

import com.eventbooking.auth_service.dto.*;
import com.eventbooking.auth_service.model.User;
import com.eventbooking.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor 
public class AuthService {
    private final UserRepository userRepository;

    public AuthResponse register(RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email ya está registrado");
        }

        User user = User.builder() 
                .email(request.getEmail())
                .password(request.getPassword()) // Más adelante lo encriptaremos con BCrypt
                .role("USER")
                .build();

        userRepository.save(user);

        // Simulamos la devolución de token
        String token = "mock-token-for" + user.getEmail(); // Aquí deberías generar un token JWT real

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");                
        }

        // Simulamos la devolución de token
        String token = "mock-token-for" + user.getEmail(); // Aquí deberías generar un token JWT real

        return new AuthResponse(token);
    }
}