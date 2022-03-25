package com.softtech.finalproject.service.sellingpricestrategy;

import java.math.BigDecimal;
import com.softtech.finalproject.model.ProductTypeEnum;
import com.softtech.finalproject.service.EntityService.ProductCategoryEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service(ProductTypeEnum.Name.CLEANING)
@RequiredArgsConstructor
public class Cleaning implements ProductType {
    private final ProductCategoryEntityService taxRatesEntityService;
    @Override
    public BigDecimal apply(BigDecimal taxFreePrice) {
        Double tax= taxRatesEntityService.findTaxRatesByProductType(ProductTypeEnum.CLEANING);
        return taxFreePrice.add(taxFreePrice.multiply(new BigDecimal(tax)));
    }
}
