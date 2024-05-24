package com.metaphorce.shop_all.domain;

import com.metaphorce.shop_all.enums.Category;

public record CartDetailsResponse(

        String description,

        String code,

        Category category,

        Integer number_pieces,

        Double amount
) {
}
