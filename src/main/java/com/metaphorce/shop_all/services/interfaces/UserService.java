package com.metaphorce.shop_all.services.interfaces;

import com.metaphorce.shop_all.domain.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAll();

    void deleteUser(Long id);
}
