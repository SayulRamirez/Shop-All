package com.metaphorce.shop_all.repositories;

import com.metaphorce.shop_all.entities.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, Long> {

    @Query("select sum(c.numberPieces) from CartDetails c where c.cart.id =:cart")
    Optional<Integer> sumNumberProducts(Long cart);

    @Query("select sum(c.amount) from CartDetails c where c.cart.id =:cart")
    Optional<Double> sumAmount(Long cart);

    @Query("select c from CartDetails c where c.cart.id =:cart")
    List<CartDetails> findCartDetailsByCartId(Long cart);

    @Query("select c.id from CartDetails c where c.cart.id =:cartId and c.product.id =:productId")
    Optional<Long> getId(Long productId, Long cartId);
}
