package com.metaphorce.shop_all.domain;

import io.swagger.v3.oas.annotations.media.Schema;

public record AddCartRequest(

        @Schema(example = "1")
        Long product_id,

        @Schema(example = "1")
        Long user_id,

        @Schema(example = "4")
        Integer pieces
) {
}
