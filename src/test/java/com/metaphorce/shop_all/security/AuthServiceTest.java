package com.metaphorce.shop_all.security;

import com.metaphorce.shop_all.domain.AuthResponse;
import com.metaphorce.shop_all.domain.LoginRequest;
import com.metaphorce.shop_all.domain.UserRequest;
import com.metaphorce.shop_all.entities.User;
import com.metaphorce.shop_all.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService underTest;

    @Test
    void whenLoginTheEmailIsNotExists() {

        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        underTest.register(new UserRequest("any body", "any@example.com", "1234"));

        LoginRequest request = new LoginRequest("anyemail@example.com", "1234");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> underTest.login(request));

    }

    @Test
    void whenLoginThePasswordIsIncorrect() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        underTest.register(new UserRequest("any body", "any@example.com", "1234"));

        LoginRequest request = new LoginRequest("any@example.com", "iub9y");

        assertThrows(EntityNotFoundException.class, () -> underTest.login(request));
    }

    @Test
    void whenLoginIsSuccessful() {

        LoginRequest request = new LoginRequest("any@example.com", "1234");

        when(userRepository.findByEmail(request.email()))
                .thenReturn(Optional.of(User.builder()
                        .id(1L)
                        .name("juan")
                        .email("juan@example.com")
                        .password("qwert").build()));

        when(jwtService.getToken(any(UserDetails.class))).thenReturn("token");

        AuthResponse login = underTest.login(request);

        assertFalse(login.token().isBlank());
    }

    @Test
    void whenRegisterTheEmailAlreadyExists() {

        UserRequest request = new UserRequest("juan", "juan@example.com", "1234");

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> underTest.register(request));
    }

    @Test
    void whenRegisterIsSuccessful() {

        UserRequest request = new UserRequest("juan", "juan@example.com", "1234");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        when(passwordEncoder.encode(anyString())).thenReturn("qwertt");

        when(userRepository.save(any(User.class)))
                .thenReturn(User.builder()
                        .id(1L)
                        .name("juan")
                        .email("juan@example.com")
                        .password("qwert").build());

        when(jwtService.getToken(any(UserDetails.class))).thenReturn("token");

        AuthResponse response = underTest.register(request);

        assertFalse(response.token().isBlank());
        verify(userRepository, times(1)).save(any(User.class));
    }
}