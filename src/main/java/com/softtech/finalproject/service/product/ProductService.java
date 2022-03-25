package com.softtech.finalproject.service.product;

import com.softtech.finalproject.dto.product.*;
import com.softtech.finalproject.model.ProductTypeEnum;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@Transactional
public interface ProductService {
    AllCategoryDetailsDto getAllCategoryDetails(ProductTypeEnum productTypeEnum);
    ProductResponse createProduct(ProductDto productDto);
    List<ProductResponse> getProductsList();
    void deleteProduct(Long id);
    ProductResponse updateProduct(UpdateProductDto updateProductDto);
    ProductResponse updateProductPrice(UpdateProductPriceDto updateProductPriceDto);
    List<ProductResponse> getAllByProductType(ProductTypeEnum productTypeEnum);
    List<ProductResponse> findAllByProductPriceBetween(BigDecimal startPrice, BigDecimal endPrice);
}
