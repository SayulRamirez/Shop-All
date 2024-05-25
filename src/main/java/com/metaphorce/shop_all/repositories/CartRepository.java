package com.metaphorce.shop_all.repositories;

import com.metaphorce.shop_all.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.id =:user")
    Optional<Cart> findCartByUser(Long user);

}
