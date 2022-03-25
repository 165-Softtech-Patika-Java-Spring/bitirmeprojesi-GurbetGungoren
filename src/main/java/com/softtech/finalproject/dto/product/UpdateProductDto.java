package com.softtech.finalproject.dto.product;

import com.softtech.finalproject.model.ProductTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {
    private Long id;
    private String productName;
    private BigDecimal taxFreeSellingPrice;
    private ProductTypeEnum productType;
}
