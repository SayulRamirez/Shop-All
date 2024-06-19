package com.metaphorce.shop_all.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddCartRequest(

        @NotNull(message = "The field must not be null")
        @Schema(example = "1")
        Long product_id,

        @NotNull(message = "The field must not be null")
        @Schema(example = "1")
        Long user_id,

        @NotNull(message = "The field must not be null")
        @Min(message = "The value must be greater that 0", value = 1)
        @Max(message = "The value must not be greater that 10", value = 10)
        @Schema(example = "4")
        Integer pieces
) {
}
