package com.metaphorce.shop_all.services;

import com.metaphorce.shop_all.domain.AddCartRequest;
import com.metaphorce.shop_all.entities.Product;
import com.metaphorce.shop_all.exceptions.NotEnoughStockException;
import com.metaphorce.shop_all.repositories.CartDetailsRepository;
import com.metaphorce.shop_all.repositories.CartRepository;
import com.metaphorce.shop_all.repositories.ProductRepository;
import com.metaphorce.shop_all.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void whenAddProductTheUserNotExists() {

        AddCartRequest request = new AddCartRequest(1L, 1L, 3);

        when(userRepository.existsUserByIdAndActiveIsTrue(any(Long.class))).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> underTest.addProduct(request));
        verify(userRepository, times(1)).existsUserByIdAndActiveIsTrue(any(Long.class));
        verify(productRepository, never()).findById(any(Long.class));
    }

    @Test
    void whenAddProductTheUserExistsButNotProduct() {

        AddCartRequest request = new AddCartRequest(1L, 1L, 3);

        when(userRepository.existsUserByIdAndActiveIsTrue(any(Long.class))).thenReturn(true);

        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> underTest.addProduct(request));
        verify(userRepository, times(1)).existsUserByIdAndActiveIsTrue(any(Long.class));
        verify(productRepository, times(1)).findById(any(Long.class));
        verify(cartRepository, never()).findCartByUser(any(Long.class));
    }

    @Test
    void whenThereIsInefficientStock() {

        AddCartRequest request = new AddCartRequest(1L, 1L, 3);
        Product product = Product.builder()
                        .id(1L).stock(2).build();

        when(userRepository.existsUserByIdAndActiveIsTrue(any(Long.class))).thenReturn(true);

        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(product));

        assertThrows(NotEnoughStockException.class, () -> underTest.addProduct(request));
        verify(userRepository, times(1)).existsUserByIdAndActiveIsTrue(any(Long.class));
        verify(productRepository, times(1)).findById(any(Long.class));
        verify(cartRepository, never()).findCartByUser(any(Long.class));
    }

}

// cuando el producto no esta en el carrito

// cuando el producto ya esta en el carrito
