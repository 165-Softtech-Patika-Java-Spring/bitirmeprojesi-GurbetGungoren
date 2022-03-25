package com.softtech.finalproject.service.sellingpricestrategy;

import java.math.BigDecimal;

import com.softtech.finalproject.model.ProductTypeEnum;
import com.softtech.finalproject.service.EntityService.ProductCategoryEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(ProductTypeEnum.Name.TECHNOLOGY)
@RequiredArgsConstructor
@Transactional
public class Technology implements ProductType {
    private final ProductCategoryEntityService taxRatesEntityService;
    @Override
    public BigDecimal apply(BigDecimal taxFreePrice) {
        Double tax= taxRatesEntityService.findTaxRatesByProductType(ProductTypeEnum.TECHNOLOGY);
        return taxFreePrice.add(taxFreePrice.multiply(new BigDecimal(tax)));
    }
}
