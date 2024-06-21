package com.metaphorce.shop_all.services;

import com.metaphorce.shop_all.domain.UserRequest;
import com.metaphorce.shop_all.domain.UserResponse;
import com.metaphorce.shop_all.entities.Cart;
import com.metaphorce.shop_all.entities.User;
import com.metaphorce.shop_all.repositories.CartRepository;
import com.metaphorce.shop_all.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private UserServiceImpl underTest;

    @Test
    void whenAUserRegisterTheParametersAreCorrect() {

        // give
        UserRequest request = new UserRequest("juan", "juan1234@example.com");

        // when
        underTest.register(request);

        // that
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());

        User requestCaptured = argumentCaptor.getValue();
        User user = User.builder().name(request.name()).email(request.email()).build();

        assertEquals(user.getName(), requestCaptured.getName());
        assertEquals(user.getEmail(), requestCaptured.getEmail());
    }

    @Test
    void whenRegistrationIsSuccessful() {
        UserRequest request = new UserRequest("juan", "juan1234@example.com");

        User user = User.builder().name(request.name()).email(request.email()).build();

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = underTest.register(request);

        assertEquals(user.getName(), response.name());
        assertEquals(user.getEmail(), response.email());

        verify(userRepository, times(1)).save(any(User.class));
        verify(cartRepository, times(1)).save(any(Cart.class));

    }

    @Test
    void whenTheUserAlreadyExists() {
        UserRequest request = new UserRequest("repeated", "juan1234@example.com");

        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> underTest.register(request));
        verify(userRepository, times(1)).existsByEmail(any(String.class));
    }

    @Test
    void whenGetAllUsersIsEmpty() {

        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<UserResponse> response = underTest.getAll();

        verify(userRepository, times(1)).findAll();
        assertTrue(response.isEmpty());
    }

    @Test
    void whenGetAllUserIsNotEmpty() {

        List<User> users = List.of(
                User.builder().name("first user").email("first@example.com").build(),
                User.builder().name("second user").email("second@example.com").build()
        );

        when(userRepository.findAll()).thenReturn(users);

        List<UserResponse> responses = underTest.getAll();

        verify(userRepository, times(1)).findAll();
        assertFalse(responses.isEmpty());
        assertEquals(2, responses.size());
    }
}
