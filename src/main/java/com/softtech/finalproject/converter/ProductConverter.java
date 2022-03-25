package com.softtech.finalproject.converter;

import com.softtech.finalproject.dto.product.ProductDto;
import com.softtech.finalproject.dto.product.ProductResponse;
import com.softtech.finalproject.dto.product.UpdateProductDto;
import com.softtech.finalproject.model.ProductCategory;
import com.softtech.finalproject.model.ProductEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class ProductConverter {
    public ProductEntity convertToProduct(ProductDto productDto, BigDecimal price, ProductCategory productCategory){
        ProductEntity product= new ProductEntity();
        product.setId(product.getId());
        product.setProductName(productDto.getProductName());
        product.setProductCategory(productCategory);
        product.setTaxFreeSellingPrice(productDto.getTaxFreeSellingPrice());
        product.setProductPrice(price);
        return product;
    }
    public ProductEntity convertToProductfromUpdateDto(UpdateProductDto productDto, BigDecimal price, ProductCategory productCategory){
        ProductEntity product= new ProductEntity();
        product.setId(product.getId());
        product.setProductName(productDto.getProductName());
        product.setProductCategory(productCategory);
        product.setTaxFreeSellingPrice(productDto.getTaxFreeSellingPrice());
        product.setProductPrice(price);
        return product;
    }
    public ProductResponse convertToProductResponse(ProductEntity productEntity){
        ProductResponse productResponse= new ProductResponse();
        productResponse.setId(productEntity.getId());
        productResponse.setProductPrice(productEntity.getProductPrice());
        productResponse.setProductType(productEntity.getProductCategory().getProductType());
        productResponse.setProductName(productEntity.getProductName());
        productResponse.setTaxFreeSellingPrice(productEntity.getTaxFreeSellingPrice());
        return productResponse;
    }
}
