package com.softtech.finalproject.service.EntityService;

import com.softtech.finalproject.dao.ProductDao;
import com.softtech.finalproject.gen.service.BaseEntityService;
import com.softtech.finalproject.model.ProductEntity;
import com.softtech.finalproject.model.ProductTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ProductEntityService extends BaseEntityService<ProductEntity,ProductDao> {
    public ProductEntityService(ProductDao dao) {
        super(dao);
    }
    public List<ProductEntity> findAllByProductType(ProductTypeEnum productTypeEnum) {
        return getDao().findAllByProductCategory_ProductType(productTypeEnum);
    }
    public List<ProductEntity> findAllByProductPriceBetween(BigDecimal startPrice, BigDecimal endPrice) {
        return getDao().findAllByProductPriceBetween(startPrice, endPrice);
    }

}