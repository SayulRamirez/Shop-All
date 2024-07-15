package com.metaphorce.shop_all.repositories;

import com.metaphorce.shop_all.entities.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CartRepositoryTest {

    @Autowired
    private CartRepository underTest;

    @Test
    void whenFindByIdCartNotExists() {

        Optional<Cart> result = underTest.findById(0L);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void whenFindCartByIdExists() {

        Cart cart = Cart.builder().build();
        Cart response = underTest.save(cart);

        Optional<Cart> result = underTest.findById(response.getId());

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void whenSaveCart() {

        Cart cart = Cart.builder().build();

        Cart result = underTest.save(cart);

        assertThat(result.getId() > 0).isTrue();
    }

    @Test
    void whenUpdateCart() {
        Cart cart = Cart.builder().build();

        Cart result = underTest.save(cart);

        result.setNumberProducts(1);
        result.setAmount(231.31);

        Cart update = underTest.save(result);

        assertThat(update.getAmount()).isEqualTo(result.getAmount());
        assertThat(update.getNumberProducts()).isEqualTo(result.getNumberProducts());
    }
}
