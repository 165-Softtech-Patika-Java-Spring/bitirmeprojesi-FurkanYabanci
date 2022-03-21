package com.softtech.market.enums;

public enum ProductErrorMessage implements BaseErrorMessage{

    PRODUCT_NOT_FOUND("Product Not Found!");

    private String message;
    ProductErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
