package com.metaphorce.shop_all.domain;

public record CartResponse(

        String name,

        Integer number_products,

        Double amount
) {
}
