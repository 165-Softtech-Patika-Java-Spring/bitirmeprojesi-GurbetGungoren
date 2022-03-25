package com.softtech.finalproject.service.product;

import com.softtech.finalproject.converter.ProductConverter;
import com.softtech.finalproject.dto.product.*;
import com.softtech.finalproject.enums.ProductErrorMessage;
import com.softtech.finalproject.gen.exceptions.GenBusinessException;
import com.softtech.finalproject.gen.exceptions.ItemNotFoundException;
import com.softtech.finalproject.model.ProductCategory;
import com.softtech.finalproject.model.ProductEntity;
import com.softtech.finalproject.model.ProductTypeEnum;
import com.softtech.finalproject.service.EntityService.ProductCategoryEntityService;
import com.softtech.finalproject.service.EntityService.ProductEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImp implements ProductService{
    private final ProductConverter productConverter;
    private final ProductEntityService productEntityService;
    private final ProductCategoryEntityService productCategoryEntityService;
    @Override
    public ProductResponse createProduct(ProductDto productDto) {
        productValidator(productDto);
        //BigDecimal sellingPrice=sellingPriceStrategy.calculateSellingPrice(productDto.getProductType(),productDto.getTaxFreeSellingPrice());
        ProductCategory productCategory = productCategoryEntityService.findProductCategoryByProductType(productDto.getProductType());
        //BigDecimal sellingPrice=productDto.getTaxFreeSellingPrice().add(productDto.getTaxFreeSellingPrice().multiply(new BigDecimal(productCategory.getTaxRates())));
        BigDecimal sellingPrice=calculateSellingPrice(productDto.getTaxFreeSellingPrice(), productCategory.getTaxRates());
        ProductEntity productEntity= productConverter.convertToProduct(productDto,sellingPrice,productCategory);
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
                .map(productConverter::convertToProductResponse)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteProduct(Long id) {
        ProductEntity entity = productEntityService.getByIdWithControl(id);
        productEntityService.delete(entity);
    }
    @Override
    public ProductResponse updateProduct(UpdateProductDto updateProductDto){

        controlIsProductExist(updateProductDto.getId());
        ProductCategory productCategory = productCategoryEntityService.findProductCategoryByProductType(updateProductDto.getProductType());
        //BigDecimal sellingPrice = sellingPriceStrategy.calculateSellingPrice(updateProductDto.getProductType(), updateProductDto.getTaxFreeSellingPrice());
        //BigDecimal sellingPrice=updateProductDto.getTaxFreeSellingPrice().add(updateProductDto.getTaxFreeSellingPrice().multiply(new BigDecimal(productCategory.getTaxRates())));
        BigDecimal sellingPrice=calculateSellingPrice(updateProductDto.getTaxFreeSellingPrice(),productCategory.getTaxRates());
        ProductEntity productEntity = productConverter.convertToProductfromUpdateDto(updateProductDto, sellingPrice,productCategory);
        return productConverter.convertToProductResponse(productEntityService.save(productEntity));
    }
    @Override
    public ProductResponse updateProductPrice(UpdateProductPriceDto updateProductPriceDto){
        ProductEntity productEntity = productEntityService.getByIdWithControl(updateProductPriceDto.getId());
        //BigDecimal sellingPrice = sellingPriceStrategy.calculateSellingPrice(updateProductPriceDto.getNewTaxFreePrice());
        //BigDecimal sellingPrice=updateProductPriceDto.getNewTaxFreePrice().add(updateProductPriceDto.getNewTaxFreePrice().multiply(new BigDecimal(productEntity.getProductCategory().getTaxRates())));
        BigDecimal sellingPrice=calculateSellingPrice(updateProductPriceDto.getNewTaxFreePrice(),productEntity.getProductCategory().getTaxRates());
        productEntity.setTaxFreeSellingPrice(updateProductPriceDto.getNewTaxFreePrice());
        productEntity.setProductPrice(sellingPrice);
        return productConverter.convertToProductResponse(productEntityService.save(productEntity));
    }
    @Override
    public List<ProductResponse> findAllByProductPriceBetween(BigDecimal startPrice, BigDecimal endPrice){
        return productEntityService.findAllByProductPriceBetween(startPrice, endPrice).stream()
                .map(productConverter::convertToProductResponse)
                .collect(Collectors.toList());
    }
    @Override
    public AllCategoryDetailsDto getAllCategoryDetails(ProductTypeEnum productTypeEnum){
        List<ProductEntity> productList = productEntityService.findAllByProductType(productTypeEnum);
        List<BigDecimal> priceList = productList.stream()
                .map(ProductEntity::getProductPrice)
                .collect(Collectors.toList());
        BigDecimal max = priceList.stream()
                .max(Comparator.naturalOrder()).get();
        BigDecimal min = priceList.stream()
                .min(Comparator.naturalOrder()).get();
        int numberOfProducts = priceList.size();
        Double taxRates = productCategoryEntityService.findTaxRatesByProductType(productTypeEnum);
        BigDecimal sum = priceList.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal average= sum.divide(new BigDecimal(numberOfProducts));

        return AllCategoryDetailsDto.builder()
                .productType(productTypeEnum)
                .averagePrice(average)
                .minPrice(min)
                .numberOfProducts(numberOfProducts)
                .maxPrice(max)
                .taxRates(taxRates)
                .build();
    }
    private void controlIsProductExist(Long id){
        boolean isExist= productEntityService.existsById(id);
        if (!isExist){
            throw new ItemNotFoundException(ProductErrorMessage.PRODUCT_ERROR_MESSAGE);
        }
    }
    private void productValidator(ProductDto productDto){
        ProductValidation.ValidationResult validationResult = ProductValidation.isProductsFieldNotEmpty().
                and(ProductValidation.isProductPriceHigherThanZero()).apply(productDto);
        if (validationResult!= ProductValidation.ValidationResult.SUCCESS){
            throw new GenBusinessException(validationResult.getExceptionType());
        }
    }
    private BigDecimal calculateSellingPrice(BigDecimal taxFreePrice, Double taxRates){
        return taxFreePrice.add(taxFreePrice.multiply(new BigDecimal(taxRates)));
    }
}

