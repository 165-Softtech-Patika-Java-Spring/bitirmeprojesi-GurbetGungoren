package com.softtech.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductPriceDto {
    private Long id;
    private BigDecimal newTaxFreePrice;
}
