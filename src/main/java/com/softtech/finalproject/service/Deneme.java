package com.softtech.finalproject.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
public class Deneme {
    ProductType productType;
    Double taxFree;

    public Deneme(ProductType productType, Double taxFree) {
        this.productType = productType;
        this.taxFree = taxFree;
    }
}
