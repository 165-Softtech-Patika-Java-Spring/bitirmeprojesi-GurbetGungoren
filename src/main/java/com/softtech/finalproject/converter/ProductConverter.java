package com.softtech.finalproject.converter;

import com.softtech.finalproject.dto.ProductDto;
import com.softtech.finalproject.dto.ProductResponse;
import com.softtech.finalproject.dto.UpdateProductDto;
import com.softtech.finalproject.model.ProductEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class ProductConverter {
    public ProductEntity convertToProduct(ProductDto productDto, BigDecimal price){
        ProductEntity product= new ProductEntity();
        product.setId(product.getId());
        product.setProductName(productDto.getProductName());
        product.setProductType(productDto.getProductType());
        product.setTaxFreeSellingPrice(productDto.getTaxFreeSellingPrice());
        product.setProductPrice(price);
        return product;
    }
    public ProductEntity convertToProductfromUpdateDto(UpdateProductDto productDto, BigDecimal price){
        ProductEntity product= new ProductEntity();
        product.setId(product.getId());
        product.setProductName(productDto.getProductName());
        product.setProductType(productDto.getProductType());
        product.setTaxFreeSellingPrice(productDto.getTaxFreeSellingPrice());
        product.setProductPrice(price);
        return product;
    }
    public ProductResponse convertToProductResponse(ProductEntity productEntity){
        ProductResponse productResponse= new ProductResponse();
        productResponse.setId(productEntity.getId());
        productResponse.setProductPrice(productEntity.getProductPrice());
        productResponse.setProductType(productEntity.getProductType());
        productResponse.setProductName(productEntity.getProductName());
        productResponse.setTaxFreeSellingPrice(productEntity.getTaxFreeSellingPrice());
        return productResponse;
    }
}
