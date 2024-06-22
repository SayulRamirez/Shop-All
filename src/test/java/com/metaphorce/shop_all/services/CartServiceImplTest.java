package com.metaphorce.shop_all.services;

import com.metaphorce.shop_all.domain.AddCartRequest;
import com.metaphorce.shop_all.domain.CartDetailsResponse;
import com.metaphorce.shop_all.domain.CartResponse;
import com.metaphorce.shop_all.entities.Cart;
import com.metaphorce.shop_all.entities.CartDetails;
import com.metaphorce.shop_all.entities.Product;
import com.metaphorce.shop_all.entities.User;
import com.metaphorce.shop_all.exceptions.NotEnoughStockException;
import com.metaphorce.shop_all.repositories.CartDetailsRepository;
import com.metaphorce.shop_all.repositories.CartRepository;
import com.metaphorce.shop_all.repositories.ProductRepository;
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

    @Test
    void whenTheProductIsNotInTheCart() {

        AddCartRequest request = new AddCartRequest(1L, 1L, 3);
        Product product = Product.builder()
                .id(1L).stock(6).price(24.40).build();

        Cart cart = Cart.builder().id(1L).user(User.builder().id(1L).build()).numberProducts(1).amount(24.40).build();

        when(userRepository.existsUserByIdAndActiveIsTrue(any(Long.class))).thenReturn(true);

        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(product));

        when(cartRepository.findCartByUser(any(Long.class))).thenReturn(Optional.of(cart));

        when(cartDetailsRepository.getId(any(Long.class), any(Long.class))).thenReturn(Optional.of(1L));

        when(cartDetailsRepository.findById(any(Long.class))).thenReturn(Optional.of(CartDetails.builder().build()));

        underTest.addProduct(request);

        verify(cartDetailsRepository, times(1)).save(any(CartDetails.class));
    }

    @Test
    void whenTheProductIsInTheCart() {

        AddCartRequest request = new AddCartRequest(1L, 1L, 3);
        Product product = Product.builder()
                .id(1L).stock(6).price(24.40).build();

        Cart cart = Cart.builder().id(1L).user(User.builder().id(1L).build()).numberProducts(1).amount(24.40).build();

        when(userRepository.existsUserByIdAndActiveIsTrue(any(Long.class))).thenReturn(true);

        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(product));

        when(cartRepository.findCartByUser(any(Long.class))).thenReturn(Optional.of(cart));

        when(cartDetailsRepository.getId(any(Long.class), any(Long.class))).thenReturn(Optional.empty());

        underTest.addProduct(request);

        verify(cartDetailsRepository, times(1)).save(any(CartDetails.class));
    }

    @Test
    void whenTheyAskForGeneralCartDetails() {
        Cart cart = Cart.builder()
                .user(User.builder().name("juan").build())
                .numberProducts(2)
                .amount(45.42).build();

        when(userRepository.existsUserByIdAndActiveIsTrue(any(Long.class))).thenReturn(true);
        when(cartRepository.findCartByUser(any(Long.class))).thenReturn(Optional.of(cart));

        CartResponse response = underTest.getCart(1L);

        assertEquals(cart.getUser().getName(), response.name());
        assertEquals(cart.getNumberProducts(), response.number_products());
        assertEquals(cart.getAmount(), response.amount());
    }

    @Test
    void whenYouGetTheCartDetailsAndTheyAreEmpty() {

        Long id = 1L;
        List<CartDetails> cartDetails = new ArrayList<>();

        when(cartRepository.getIdByUser(any(Long.class))).thenReturn(Optional.of(id));

        when(cartDetailsRepository.findCartDetailsByCartId(id)).thenReturn(cartDetails);

        List<CartDetailsResponse> responses = underTest.getDetailsCart(1L);

        assertTrue(responses.isEmpty());
        verify(cartDetailsRepository, times(1)).findCartDetailsByCartId(any(Long.class));
    }

    // obtener detarlles del carrito con productos

}

