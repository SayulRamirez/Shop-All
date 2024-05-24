package com.metaphorce.shop_all.domain;

import com.metaphorce.shop_all.enums.PaymontMethod;

public record SaleRequest(

        Long userId,
        PaymontMethod method
) {
}
