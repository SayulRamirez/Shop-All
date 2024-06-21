package com.metaphorce.shop_all.services;

import com.metaphorce.shop_all.domain.UserRequest;
import com.metaphorce.shop_all.entities.User;
import com.metaphorce.shop_all.repositories.CartRepository;
import com.metaphorce.shop_all.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
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


}
