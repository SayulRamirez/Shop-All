package com.metaphorce.shop_all.domain;

import com.metaphorce.shop_all.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;

public record ProductResponse(

        @Schema(example = "Lambrusco 750 ml")
        String description,

        @Schema(example = "VINO")
        Category category,

        @Schema(example = "20")
        Integer stock,

        @Schema(example = "138.40")
        Double price
) {
}
