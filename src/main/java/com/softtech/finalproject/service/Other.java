package com.softtech.finalproject.service;

import java.math.BigDecimal;

public class Other implements ProductType {
    @Override
    public BigDecimal apply(BigDecimal taxFreePrice, Double taxRate)  {
        return taxFreePrice.add(taxFreePrice.multiply(new BigDecimal(0.18)));
    }
}
