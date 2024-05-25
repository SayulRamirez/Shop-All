package com.metaphorce.shop_all.domain;

import com.metaphorce.shop_all.enums.PaymontMethod;

import java.time.LocalDateTime;

public record SaleResponse(

        Double amount,

        Integer numberProducts,

        LocalDateTime datePurchase,

        PaymontMethod paymontMethod
) {
}
