package com.softtech.finalproject.service.productcategory;

import com.softtech.finalproject.model.ProductCategory;
import com.softtech.finalproject.model.ProductEntity;
import com.softtech.finalproject.model.ProductTypeEnum;
import com.softtech.finalproject.service.EntityService.ProductCategoryEntityService;
import com.softtech.finalproject.service.EntityService.ProductEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImp implements ProductCategoryService{
    private final ProductCategoryEntityService productCategoryEntityService;
    private final ProductEntityService productEntityService;

    @Override
    @Transactional
    public Long updateTaxRates(ProductTypeEnum productTypeEnum, Double newTaxRates){
        ProductCategory productCategory = productCategoryEntityService.findProductCategoryByProductType(productTypeEnum);
        productCategory.setTaxRates(newTaxRates);
        productCategoryEntityService.save(productCategory);
        productEntityService.saveAll(changeProductPrice(productTypeEnum,newTaxRates));
        return productCategory.getId();
    }
    private List<ProductEntity> changeProductPrice(ProductTypeEnum productTypeEnum, Double newTaxRates){
        List<ProductEntity> list = productEntityService.findAllByProductType(productTypeEnum);
        return list.stream()
                .map(productEntity -> {
                    productEntity.setProductPrice(calculateSellingPrice(productEntity.getTaxFreeSellingPrice(),newTaxRates));
                    return productEntity;
                }).collect(Collectors.toList());
    }

    private BigDecimal calculateSellingPrice(BigDecimal taxFreePrice, Double taxRates){
        return taxFreePrice.add(taxFreePrice.multiply(new BigDecimal(taxRates)));
    }

}
