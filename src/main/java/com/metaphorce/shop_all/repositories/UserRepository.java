package com.metaphorce.shop_all.repositories;

import com.metaphorce.shop_all.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsUserByIdAndActiveIsTrue(Long id);
}
