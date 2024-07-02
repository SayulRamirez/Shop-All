package com.metaphorce.shop_all.configuration;

import com.metaphorce.shop_all.entities.Cart;
import com.metaphorce.shop_all.entities.Role;
import com.metaphorce.shop_all.entities.User;
import com.metaphorce.shop_all.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        return args -> {

            User user = User.builder()
                    .name("admin application")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("admin1234"))
                    .cart(Cart.builder().build())
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(user);
        };
    }
}
