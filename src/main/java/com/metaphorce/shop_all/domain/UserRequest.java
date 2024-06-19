package com.metaphorce.shop_all.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest (

        @NotBlank(message = "The field must not be null or empty")
        @Schema(example = "Juan Martín")
        String name,

        @NotBlank(message = "The field must not be null or empty")
        @Email(message = "It must be a properly formatted email address")
        @Schema(example = "juan1234@example.com")
        String email
){}
