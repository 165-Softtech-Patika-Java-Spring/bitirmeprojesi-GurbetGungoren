package com.softtech.finalproject.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
public class Food implements ProductType {
    @Override
    public BigDecimal apply(BigDecimal taxFreePrice, Double taxRate) {
        return taxFreePrice.add(taxFreePrice.multiply(new BigDecimal(taxRate)));
    }
}
