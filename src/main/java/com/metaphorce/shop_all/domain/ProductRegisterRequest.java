package com.metaphorce.shop_all.domain;

import com.metaphorce.shop_all.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRegisterRequest(

        @Schema(example = "Jose cuervo")
        @NotBlank
        String description,

        @Schema(example = "314121")
        @NotBlank
        String code,

        @Schema(example = "TEQUILA")
        @NotBlank
        Category category,

        @Schema(example = "12")
        @NotNull
        @Positive
        Integer stock,

        @Schema(example = "1243.2")
        @NotNull
        @Positive
        Double price
) {
}
