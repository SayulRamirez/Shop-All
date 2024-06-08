package com.metaphorce.shop_all.domain;

import com.metaphorce.shop_all.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;

public record CartDetailsResponse(

        @Schema(example = "Heineken 355 ml lata")
        String description,

        @Schema(example = "1234")
        String code,

        @Schema(example = "CERVEZA")
        Category category,

        @Schema(example = "4")
        Integer number_pieces,

        @Schema(example = "97.6")
        Double amount
) {
}
