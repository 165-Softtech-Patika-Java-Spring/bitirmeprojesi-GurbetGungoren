package com.softtech.finalproject.service;

import com.softtech.finalproject.dto.ProductDto;
import com.softtech.finalproject.gen.enums.BaseErrorMessage;
import com.softtech.finalproject.gen.enums.GenErrorMessage;
import com.softtech.finalproject.model.ProductEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.function.Function;

import static com.softtech.finalproject.service.ProductValidation.ValidationResult.*;

public interface ProductValidation extends Function<ProductDto, ProductValidation.ValidationResult> {
    static ProductValidation isProductsFieldNotEmpty(){
        return productDto -> !productDto.getProductName().isBlank() && productDto.getProductType() != null ?
               SUCCESS:PRODUCT_FIELD_EMPTY;
    }
    static ProductValidation isProductPriceHigherThanZero(){
        return productDto -> productDto.getTaxFreeSellingPrice().compareTo(new BigDecimal(0)) > 0 ?
                SUCCESS :PRICE_IS_NOT_NEGATIVE_OR_ZERO ;
    }
    default ProductValidation and(ProductValidation other) {
        return productDto -> {
            ValidationResult validationResult = this.apply(productDto);
                return validationResult.equals(SUCCESS)? other.apply(productDto):validationResult;
        };
    }
    @RequiredArgsConstructor
    @Getter
    enum ValidationResult {
        SUCCESS(null),
        PRODUCT_FIELD_EMPTY(GenErrorMessage.PARAMETER_CANNOT_BE_NULL),
        PRICE_IS_NOT_NEGATIVE_OR_ZERO(GenErrorMessage.VALUE_CANNOT_BE_NEGATIVE);
        private final BaseErrorMessage exceptionType;
        }
}
