package com.softtech.finalproject.dto.product;

import com.softtech.finalproject.model.ProductTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {
    private String productName;
    private BigDecimal taxFreeSellingPrice;
    private ProductTypeEnum productType;
}
