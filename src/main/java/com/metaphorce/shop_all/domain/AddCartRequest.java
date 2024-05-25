package com.metaphorce.shop_all.domain;

public record AddCartRequest(

        Long product_id,

        Long user_id,

        Integer pieces
) {
}
