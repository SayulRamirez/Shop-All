package com.metaphorce.shop_all.exceptions;

public class NotEnoughStock extends RuntimeException {

    public NotEnoughStock(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
