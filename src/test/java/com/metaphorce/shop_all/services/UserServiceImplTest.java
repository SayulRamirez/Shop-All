package com.metaphorce.shop_all.services;

import com.metaphorce.shop_all.domain.UserResponse;
import com.metaphorce.shop_all.entities.User;
import com.metaphorce.shop_all.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @InjectMocks
    private UserServiceImpl underTest;

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

    @Test
    void whenUnsubscribingAUserDoesNotExist() {

        Long id = 1L;

        assertThrows(EntityNotFoundException.class, () -> underTest.deleteUser(id));
        verify(userRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void whenUnsubscribingAUserIsSuccessful() {

        User user = User.builder().id(1L).name("juan").email("juan@example.com").build();

        userRepository.save(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        underTest.deleteUser(user.getId());

        verify(userRepository, times(2)).save(any(User.class));
        assertFalse(user.isActive());
    }
}
