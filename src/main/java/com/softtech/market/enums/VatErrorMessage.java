package com.softtech.market.enums;

public enum VatErrorMessage implements BaseErrorMessage{

    VAT_NOT_FOUND("Vat not found!"),
    PRODUCT_TYPE_NOT_FOUND("Product type not found!");

    private String message;
    VatErrorMessage(String message) {
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
