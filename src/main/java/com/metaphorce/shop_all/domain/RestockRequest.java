package com.metaphorce.shop_all.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RestockRequest(

        @Schema(example = "123423")
        @NotBlank
        String code,

        @Schema(example = "13")
        @NotNull
        @Positive
        Integer stock
) {
}
