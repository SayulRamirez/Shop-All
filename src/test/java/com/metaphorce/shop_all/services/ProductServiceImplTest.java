package com.metaphorce.shop_all.services;

import com.metaphorce.shop_all.domain.ProductRegisterRequest;
import com.metaphorce.shop_all.domain.ProductResponse;
import com.metaphorce.shop_all.domain.RestockRequest;
import com.metaphorce.shop_all.entities.Product;
import com.metaphorce.shop_all.enums.Category;
import com.metaphorce.shop_all.repositories.ProductRepository;
import jakarta.persistence.EntityExistsException;
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
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl underTest;

    @Test
    void getAllProductsIsEmpty() {

        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        List<ProductResponse> response = underTest.getAll();

        verify(productRepository, times(1)).findAll();
        assertTrue(response.isEmpty());
    }

    @Test
    void getAllProductsIsNotEmpty() {

        List<Product> products = List.of(
                Product.builder().id(1L)
                        .description("Heineken 355 ml lata")
                        .category(Category.CERVEZA)
                        .stock(23)
                        .price(24.40).build(),
                Product.builder().id(6L)
                        .description("Azul reposado 750 ml botella")
                        .category(Category.TEQUILA)
                        .stock(12)
                        .price(280.64).build()
        );

        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponse> response = underTest.getAll();

        verify(productRepository, times(1)).findAll();

        assertFalse(response.isEmpty());
        assertEquals(2, response.size());
    }

    @Test
    void whenRestockTheProductNotExists() {

        RestockRequest request = new RestockRequest("notexits", 1);

        when(productRepository.findByCode(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> underTest.restock(request));
    }

    @Test
    void whenRestockTheProductIsSuccessful() {

        Product product = Product.builder()
                .code("code")
                .stock(1).build();

        RestockRequest request = new RestockRequest(product.getCode(), 1);

        when(productRepository.findByCode(anyString())).thenReturn(Optional.of(product));

        underTest.restock(request);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void whenRegisterAProductAlreadyExists() {

        ProductRegisterRequest request = new ProductRegisterRequest(
                "any description",
                "code1234",
                Category.VINO,
                1,
                12.3
        );

        when(productRepository.existsByCode(anyString())).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> underTest.registerProduct(request));
    }

    @Test
    void whenRegisterAProductIsSuccessful() {

        ProductRegisterRequest request = new ProductRegisterRequest(
                "any description",
                "code1234",
                Category.VINO,
                1,
                12.3
        );

        when(productRepository.existsByCode(anyString())).thenReturn(false);

        underTest.registerProduct(request);

        verify(productRepository, times(1)).save(any(Product.class));
    }
}
