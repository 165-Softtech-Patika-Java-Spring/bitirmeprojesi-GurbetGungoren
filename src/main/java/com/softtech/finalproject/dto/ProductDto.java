package com.softtech.finalproject.dto;

import com.softtech.finalproject.model.ProductTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {

    private String productName;
    private BigDecimal taxFreeSellingPrice;
    private ProductTypeEnum productType;
}
