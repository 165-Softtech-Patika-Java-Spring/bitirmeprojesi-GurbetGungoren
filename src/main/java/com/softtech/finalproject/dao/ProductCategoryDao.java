package com.softtech.finalproject.dao;

import com.softtech.finalproject.model.ProductCategory;
import com.softtech.finalproject.model.ProductTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryDao extends JpaRepository<ProductCategory,Long> {
    ProductCategory findByProductType(ProductTypeEnum type);
}
