package com.metaphorce.shop_all.domain;

import io.swagger.v3.oas.annotations.media.Schema;

public record CartResponse(

        @Schema(description = "Nombre del usuario", example = "Juan Martín")
        String name,

        @Schema(description = "Número total de productos en el carrito", example = "4")
        Integer number_products,

        @Schema(description = "Monto total de los productos en el carrito", example = "131.42")
        Double amount
) {
}
