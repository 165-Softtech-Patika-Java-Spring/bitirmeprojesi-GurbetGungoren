package com.softtech.finalproject.service.EntityService;

import com.softtech.finalproject.dao.ProductCategoryDao;
import com.softtech.finalproject.gen.service.BaseEntityService;
import com.softtech.finalproject.model.ProductCategory;
import com.softtech.finalproject.model.ProductTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductCategoryEntityService extends BaseEntityService<ProductCategory, ProductCategoryDao> {
    public ProductCategoryEntityService(ProductCategoryDao dao) {
        super(dao);
    }
    public Double findTaxRatesByProductType(ProductTypeEnum productType){
        return getDao().findByProductType(productType).getTaxRates();
    }
    public ProductCategory findProductCategoryByProductType(ProductTypeEnum productType){
        return getDao().findByProductType(productType);
    }
}
