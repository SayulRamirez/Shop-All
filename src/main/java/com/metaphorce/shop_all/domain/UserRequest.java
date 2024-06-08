package com.metaphorce.shop_all.domain;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserRequest (

        @Schema(example = "Juan Martín")
        String name,

        @Schema(example = "juan1234@example.com")
        String email
){}
