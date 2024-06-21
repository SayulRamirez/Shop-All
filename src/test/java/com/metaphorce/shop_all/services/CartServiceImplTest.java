package com.metaphorce.shop_all.services;

import com.metaphorce.shop_all.repositories.CartDetailsRepository;
import com.metaphorce.shop_all.repositories.CartRepository;
import com.metaphorce.shop_all.repositories.ProductRepository;
import com.metaphorce.shop_all.repositories.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartDetailsRepository cartDetailsRepository;

    @InjectMocks
    private CartServiceImpl underTest;
}
