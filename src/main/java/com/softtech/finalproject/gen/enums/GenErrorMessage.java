package com.softtech.finalproject.gen.enums;

public enum GenErrorMessage implements BaseErrorMessage {
    ITEM_NOT_FOUND("Item Not Found !"),
    USERNAME_IS_USED("User name is used !"),
    VALUE_CANNOT_BE_NEGATIVE("Value can not be negative or zero !"),
    PARAMETER_CANNOT_BE_NULL("Parameter can not be null !"),
    ;
    private String message;
    GenErrorMessage(String message) {
        this.message=message;
    }
    public String getMessage() {
        return message;
    }
    @Override
    public String toString(){
        return message;
    }
}
