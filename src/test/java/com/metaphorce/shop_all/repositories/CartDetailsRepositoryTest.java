package com.metaphorce.shop_all.repositories;

import com.metaphorce.shop_all.entities.Cart;
import com.metaphorce.shop_all.entities.CartDetails;
import com.metaphorce.shop_all.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CartDetailsRepositoryTest {

    @Autowired
    private CartDetailsRepository underTest;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    private Cart cart;

    @BeforeEach
    void setup() {

        cart = Cart.builder().build();
        cartRepository.save(cart);
    }

    @Test
    void whenGetIdCartDetailsNotExists() {

        Optional<Long> result = underTest.getId(1L, 1L);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void whenSaveCartDetails() {

        CartDetails result = underTest.save(CartDetails.builder()
                .cart(cart)
                .product(Product.builder().id(1L).build())
                .numberPieces(1)
                .amount(10.0).build());

        assertThat(result.getId() > 0).isTrue();
    }

    @Test
    void whenGetIdCartDetailsExists() {

        Product product = productRepository.findById(1L).orElseThrow();

        CartDetails cartDetails = underTest.save(CartDetails.builder()
                .cart(cart)
                .product(product)
                .numberPieces(1)
                .amount(10.0).build());

        Optional<Long> result = underTest.getId(product.getId(), cart.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(cartDetails.getId());
    }

    @Test
    void whenFindCartDetailsByCartIdNotExists() {

        List<CartDetails> result = underTest.findCartDetailsByCartId(0L);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void whenFindCartDetailsByCartIdExists() {

        Product product = productRepository.findById(1L).orElseThrow();
        Product product2 = productRepository.findById(2L).orElseThrow();

        underTest.save(CartDetails.builder()
                .cart(cart)
                .product(product)
                .numberPieces(1)
                .amount(10.0).build());

        underTest.save(CartDetails.builder()
                .cart(cart)
                .product(product2)
                .numberPieces(1)
                .amount(10.0).build());

        List<CartDetails> result = underTest.findCartDetailsByCartId(cart.getId());

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.size() == 2).isTrue();
    }

    @Test
    void whenDeleteCartDetailsById() {

        Product product = productRepository.findById(1L).orElseThrow();

        CartDetails cartDetails = underTest.save(CartDetails.builder()
                .cart(cart)
                .product(product)
                .numberPieces(1)
                .amount(10.0).build());

        underTest.deleteById(cartDetails.getId());

        Optional<CartDetails> result = underTest.findById(cartDetails.getId());

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void whenSumNumberProducts() {
        Product product = productRepository.findById(1L).orElseThrow();
        Product product2 = productRepository.findById(2L).orElseThrow();

        underTest.save(CartDetails.builder()
                .cart(cart)
                .product(product)
                .numberPieces(1)
                .amount(10.0).build());

        underTest.save(CartDetails.builder()
                .cart(cart)
                .product(product2)
                .numberPieces(1)
                .amount(10.0).build());

        Optional<Integer> numberProducts = underTest.sumNumberProducts(cart.getId());

        assertThat(numberProducts.isPresent()).isTrue();
        assertThat(numberProducts.get() == 2).isTrue();
    }

    @Test
    void whenSumAmounts() {
        Product product = productRepository.findById(1L).orElseThrow();
        Product product2 = productRepository.findById(2L).orElseThrow();

        underTest.save(CartDetails.builder()
                .cart(cart)
                .product(product)
                .numberPieces(1)
                .amount(10.0).build());

        underTest.save(CartDetails.builder()
                .cart(cart)
                .product(product2)
                .numberPieces(1)
                .amount(10.0).build());

        Optional<Double> numberProducts = underTest.sumAmount(cart.getId());

        assertThat(numberProducts.isPresent()).isTrue();
        assertThat(numberProducts.get() == 20.0).isTrue();
    }
}
