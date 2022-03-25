package com.softtech.finalproject.service.sellingpricestrategy;

import com.softtech.finalproject.model.ProductTypeEnum;
import com.softtech.finalproject.service.EntityService.ProductCategoryEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service(ProductTypeEnum.Name.FOOD)
@RequiredArgsConstructor
public class Food implements ProductType {
    private final ProductCategoryEntityService taxRatesEntityService;
    @Override
    public BigDecimal apply(BigDecimal taxFreePrice) {
        Double tax= taxRatesEntityService.findTaxRatesByProductType(ProductTypeEnum.FOOD);
        return taxFreePrice.add(taxFreePrice.multiply(new BigDecimal(tax)));
    }
}
