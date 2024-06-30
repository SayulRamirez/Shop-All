package com.metaphorce.shop_all.security;

import com.metaphorce.shop_all.domain.AuthResponse;
import com.metaphorce.shop_all.domain.LoginRequest;
import com.metaphorce.shop_all.domain.UserRequest;
import com.metaphorce.shop_all.entities.Cart;
import com.metaphorce.shop_all.entities.Role;
import com.metaphorce.shop_all.entities.User;
import com.metaphorce.shop_all.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(LoginRequest request) {

        return new AuthResponse(request.toString());
    }

    public AuthResponse register(UserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EntityExistsException("User already exists");
        }

        User user = userRepository.save(
                User.builder()
                        .name(request.name())
                        .email(request.email())
                        .password(passwordEncoder.encode(request.password()))
                        .role(Role.USER)
                        .cart(Cart.builder().build()).build());

        return new AuthResponse(jwtService.getToken(user));
    }
}
