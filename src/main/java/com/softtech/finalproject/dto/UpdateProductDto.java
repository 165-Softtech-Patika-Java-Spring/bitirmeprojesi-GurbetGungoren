package com.softtech.finalproject.dto;

import com.softtech.finalproject.model.ProductTypeEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDto {
    private Long id;
    private String productName;
    private BigDecimal taxFreeSellingPrice;
    private ProductTypeEnum productType;
}
