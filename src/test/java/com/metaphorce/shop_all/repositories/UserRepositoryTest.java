package com.metaphorce.shop_all.repositories;

import com.metaphorce.shop_all.entities.Role;
import com.metaphorce.shop_all.entities.User;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .name("under test")
                .email("undertest@gmail.com")
                .password("1234")
                .role(Role.USER)
                .build();
    }

    @Test
    void whenSaveUser() {

        User result = underTest.save(user);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isGreaterThan(0);
    }

    @Test
    void whenFindByIdIsNull() {

        Optional<User> userNull = underTest.findById(1L);

        assertThat(userNull.isEmpty()).isTrue();
    }

    @Test
    void whenFindByIdIsNotNull() {
        User save = underTest.save(user);

        Optional<User> result = underTest.findById(save.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void whenFindAllIsEmpty() {

        List<User> users = underTest.findAll();

        assertThat(users.isEmpty()).isTrue();
    }

    @Test
    void whenFindAllIsNotEmpty() {
        User secondUser = User.builder()
                .name("second")
                .email("second@example.com")
                .password("second")
                .role(Role.USER)
                .build();

        underTest.saveAll(List.of(user, secondUser));

        List<User> users = underTest.findAll();

        assertThat(users.isEmpty()).isFalse();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void whenFindByEmailIsNull() {

        Optional<User> result = underTest.findByEmail("notexists@example.com");

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void whenFindByEmailIsNotNull() {

        underTest.save(user);

        Optional<User> result = underTest.findByEmail(user.getEmail());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void whenExistsByEmailIsFalse() {

        boolean result = underTest.existsByEmail("notexists@example.com");

        assertThat(result).isFalse();
    }

    @Test
    void whenExistsByEmailIsTrue() {

        underTest.save(user);

        boolean result = underTest.existsByEmail(user.getEmail());

        assertThat(result).isTrue();
    }
}
