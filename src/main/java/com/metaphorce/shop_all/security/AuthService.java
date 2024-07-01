package com.metaphorce.shop_all.security;

import com.metaphorce.shop_all.domain.AuthResponse;
import com.metaphorce.shop_all.domain.LoginRequest;
import com.metaphorce.shop_all.domain.UserRequest;
import com.metaphorce.shop_all.entities.Cart;
import com.metaphorce.shop_all.entities.Role;
import com.metaphorce.shop_all.entities.User;
import com.metaphorce.shop_all.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        UserDetails user = userRepository.findByEmail(request.email()).orElseThrow(() -> new EntityNotFoundException("Not exists user whit email " + request.email()));

        String token = jwtService.getToken(user);

        return new AuthResponse(token);
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
