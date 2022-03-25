package com.softtech.finalproject.service.productcategory;

import com.softtech.finalproject.model.ProductTypeEnum;

public interface ProductCategoryService {
    Long updateTaxRates(ProductTypeEnum productTypeEnum, Double newTaxRates);
}
