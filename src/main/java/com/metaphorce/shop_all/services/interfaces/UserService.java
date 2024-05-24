package com.metaphorce.shop_all.services.interfaces;

import com.metaphorce.shop_all.domain.UserRequest;
import com.metaphorce.shop_all.domain.UserResponse;
import com.metaphorce.shop_all.entities.User;

import java.util.List;

public interface UserService {

    List<UserResponse> getAll();

    UserResponse register(UserRequest request);

    void deleteUser(Long id);
}
