package com.softtech.finalproject.converter;

import com.softtech.finalproject.dto.ProductDto;
import com.softtech.finalproject.dto.ProductResponse;
import com.softtech.finalproject.dto.UpdateProductDto;
import com.softtech.finalproject.dto.UpdateProductPriceDto;
import com.softtech.finalproject.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductMapper INSTANCE= Mappers.getMapper(ProductMapper.class);
    ProductEntity convertToProductEntity(ProductDto productDto);
    ProductResponse convertToProductResponse(ProductEntity productEntity);
    List<ProductDto> convertToProductDtoList(List<ProductEntity> productList);
    ProductEntity convertToProduct(UpdateProductDto updateProductDto);

}
