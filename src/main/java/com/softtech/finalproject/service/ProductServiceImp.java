package com.softtech.finalproject.service;

import com.softtech.finalproject.converter.ProductConverter;
import com.softtech.finalproject.converter.ProductMapper;
import com.softtech.finalproject.dao.ProductDao;
import com.softtech.finalproject.dto.ProductDto;
import com.softtech.finalproject.dto.ProductResponse;
import com.softtech.finalproject.dto.UpdateProductDto;
import com.softtech.finalproject.dto.UpdateProductPriceDto;
import com.softtech.finalproject.enums.ProductErrorMessage;
import com.softtech.finalproject.gen.exceptions.ItemNotFoundException;
import com.softtech.finalproject.model.ProductEntity;
import com.softtech.finalproject.model.ProductTypeEnum;
import com.softtech.finalproject.service.EntityService.ProductEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{
    private final ProductConverter productConverter;
    private final SellingPriceStrategy sellingPriceStrategy;
    private final ProductEntityService productEntityService;
    private final ProductDao productDao;

    @Override
    public ProductResponse createProduct(ProductDto productDto) {
        BigDecimal sellingPrice=sellingPriceStrategy.calculateSellingPrice(productDto.getProductType(),productDto.getTaxFreeSellingPrice());
        ProductEntity productEntity= productConverter.convertToProduct(productDto,sellingPrice);
        ProductEntity product = productEntityService.save(productEntity);
        return productConverter.convertToProductResponse(product);
    }
    @Override
    public List<ProductResponse> getProductsList(){
         return productEntityService.findAll().stream()
                 .map(productConverter::convertToProductResponse)
                 .collect(Collectors.toList());
    }
    @Override
    public List<ProductResponse> getAllByProductType(ProductTypeEnum productTypeEnum){
        return productEntityService.findAllByProductType(productTypeEnum).stream()
                .map(productEntity -> productConverter.convertToProductResponse(productEntity))
                .collect(Collectors.toList());
    }
    @Override
    public Object findById(Long id) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        ProductEntity entity = productEntityService.getByIdWİthControl(id);
        productEntityService.delete(entity);
    }
    @Override
    public ProductResponse updateProduct(UpdateProductDto updateProductDto){
        controlIsProductExist(updateProductDto.getId());
        BigDecimal sellingPrice = sellingPriceStrategy.calculateSellingPrice(updateProductDto.getProductType(), updateProductDto.getTaxFreeSellingPrice());
        ProductEntity productEntity = productConverter.convertToProductfromUpdateDto(updateProductDto, sellingPrice);
        return ProductMapper.INSTANCE.convertToProductResponse(productEntityService.save(productEntity));
    }

    @Override
    public ProductResponse updateProductPrice(UpdateProductPriceDto updateProductPriceDto){
        ProductEntity productEntity = productEntityService.getByIdWİthControl(updateProductPriceDto.getId());
        BigDecimal sellingPrice = sellingPriceStrategy.calculateSellingPrice(productEntity.getProductType(), updateProductPriceDto.getNewTaxFreePrice());
        productEntity.setTaxFreeSellingPrice(updateProductPriceDto.getNewTaxFreePrice());
        productEntity.setProductPrice(sellingPrice);
        return ProductMapper.INSTANCE.convertToProductResponse(productEntityService.save(productEntity));
    }
    @Override
    public List<ProductResponse> findAllByProductPriceBetween(BigDecimal startPrice, BigDecimal endPrice){
        return productEntityService.findAllByProductPriceBetween(startPrice, endPrice).stream()
                .map(productEntity -> productConverter.convertToProductResponse(productEntity))
                .collect(Collectors.toList());
    }

    private void controlIsProductExist(Long id){
        boolean isExist= productEntityService.existsById(id);
        if (!isExist){
            throw new ItemNotFoundException(ProductErrorMessage.PRODUCT_ERROR_MESSAGE);
        }
    }
}

