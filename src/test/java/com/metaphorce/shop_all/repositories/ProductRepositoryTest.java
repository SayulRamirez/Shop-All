package com.metaphorce.shop_all.repositories;

import com.metaphorce.shop_all.entities.Product;
import com.metaphorce.shop_all.enums.Category;
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
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository underTest;

    @Test
    void whenFindAllIsNotNull() {

        List<Product> products = underTest.findAll();

        assertThat(products.isEmpty()).isFalse();
        assertThat(products.size()).isGreaterThan(1);
    }

    @Test
    void whenFindByCodeIsNull() {

        Optional<Product> product = underTest.findByCode("notexists");

        assertThat(product.isEmpty()).isTrue();
    }

    @Test
    void whenFindByCodeIsNotNull() {

        Optional<Product> product = underTest.findByCode("7890");

        assertThat(product.isPresent()).isTrue();
        assertThat(product.get().getDescription()).isEqualTo("Bacardi 750 ml botella vidrio");
    }

    @Test
    void whenSaveProduct() {
        Product product = Product.builder()
                .description("new product")
                .code("test")
                .category(Category.VINO)
                .stock(10)
                .price(128.3)
                .build();

        Product result = underTest.save(product);

        assertThat(result.getId()).isGreaterThan(1);
        assertThat(result.getCode()).isEqualTo(product.getCode());
    }

    @Test
    void whenExistsByCodeIsFalse() {

        boolean products = underTest.existsByCode("false");

        assertThat(products).isFalse();
    }

    @Test
    void whenExistsByCodeIsTrue() {

        boolean product = underTest.existsByCode("7890");

        assertThat(product).isTrue();
    }

    @Test
    void whenFindByIdIsNull() {
        Optional<Product> product = underTest.findById(99L);

        assertThat(product.isEmpty()).isTrue();
    }

    @Test
    void whenFindByIdIsNotNull() {

        Optional<Product> product = underTest.findById(1L);

        assertThat(product.isPresent()).isTrue();
    }
}
