package com.softtech.finalproject.dao;


import com.softtech.finalproject.model.ProductEntity;
import com.softtech.finalproject.model.ProductTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findAllByProductPriceBetween(BigDecimal startPrice,BigDecimal endPrice);
    List<ProductEntity> findAllByProductCategory_ProductType(ProductTypeEnum productTypeEnum);

}
