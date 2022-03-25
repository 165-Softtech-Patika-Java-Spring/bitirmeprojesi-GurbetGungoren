package com.softtech.finalproject.dto.product;

import com.softtech.finalproject.model.ProductTypeEnum;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class AllCategoryDetailsDto {
    ProductTypeEnum productType;
    Double taxRates;
    BigDecimal minPrice;
    BigDecimal maxPrice;
    BigDecimal averagePrice;
    int numberOfProducts;
}
