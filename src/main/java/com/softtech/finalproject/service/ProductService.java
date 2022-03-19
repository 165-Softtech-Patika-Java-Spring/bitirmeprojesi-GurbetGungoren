package com.softtech.finalproject.service;

import com.softtech.finalproject.dto.ProductDto;
import com.softtech.finalproject.dto.ProductResponse;
import com.softtech.finalproject.dto.UpdateProductDto;
import com.softtech.finalproject.dto.UpdateProductPriceDto;
import com.softtech.finalproject.model.ProductTypeEnum;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductDto productDto);
    List<ProductResponse> getProductsList();
    Object findById(Long id);
    void deleteProduct(Long id);
    ProductResponse updateProduct(UpdateProductDto updateProductDto);
    ProductResponse updateProductPrice(UpdateProductPriceDto updateProductPriceDto);
    List<ProductResponse> getAllByProductType(ProductTypeEnum productTypeEnum);
    List<ProductResponse> findAllByProductPriceBetween(BigDecimal startPrice, BigDecimal endPrice);
}
