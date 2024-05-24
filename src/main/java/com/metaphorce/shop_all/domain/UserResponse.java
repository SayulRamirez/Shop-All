package com.metaphorce.shop_all.domain;

public record UserResponse(
        Long id,
        String name,
        String email,
        Boolean active
) {
}
