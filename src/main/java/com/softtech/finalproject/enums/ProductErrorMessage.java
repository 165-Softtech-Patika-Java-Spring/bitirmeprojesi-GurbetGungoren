package com.softtech.finalproject.enums;

import com.softtech.finalproject.gen.enums.BaseErrorMessage;

public enum ProductErrorMessage implements BaseErrorMessage {
    PRODUCT_ERROR_MESSAGE("Product not found !"),
    ;
    private String message;
    ProductErrorMessage(String message) {
        this.message=message;
    }
    @Override
    public String getMessage() {
        return null;
    }
    public String toString(){
        return message;
    }
}
