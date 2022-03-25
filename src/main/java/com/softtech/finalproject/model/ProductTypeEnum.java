package com.softtech.finalproject.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductTypeEnum {
    FOOD(Name.FOOD),
    STATIONARY(Name.STATIONARY),
    CLOTHING(Name.CLOTHING),
    TECHNOLOGY(Name.TECHNOLOGY),
    CLEANING(Name.CLEANING),
    OTHER(Name.OTHER);

    private final String typeName;

    public interface Name{
        String FOOD="food";
        String STATIONARY="stationary";
        String CLOTHING="clothing";
        String TECHNOLOGY="technology";
        String CLEANING="cleaning";
        String OTHER="other";
    }
}
