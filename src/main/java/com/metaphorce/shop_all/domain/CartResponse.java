package com.metaphorce.shop_all.domain;

import io.swagger.v3.oas.annotations.media.Schema;

public record CartResponse(

        @Schema(description = "User name", example = "Juan Martín")
        String name,

        @Schema(description = "Total number of products in the cart", example = "4")
        Integer number_products,

        @Schema(description = "Total amount of products in the cart", example = "131.42")
        Double amount
) {
}
