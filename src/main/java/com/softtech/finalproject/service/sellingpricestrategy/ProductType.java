package com.softtech.finalproject.service.sellingpricestrategy;

import java.math.BigDecimal;

public interface ProductType {
    BigDecimal apply(BigDecimal taxFreePrice);
}
