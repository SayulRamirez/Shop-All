package com.metaphorce.shop_all.domain;

import com.metaphorce.shop_all.enums.Category;

public record ProductResponse(

        String description,

        Category category,

        Integer stock,

        Double price
) {
}
