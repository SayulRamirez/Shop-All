package com.metaphorce.shop_all.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @Schema(example = "juan@example.com")
        @Email(message = "Formater invalid")
        @NotBlank(message = "The field must not be blank or null")
        String email,

        @Schema(example = "53cr3tK3y")
        @NotBlank(message = "The field must not be blank or null")
        String password
) {
}
