package com.softtech.finalproject.controller;

import com.softtech.finalproject.model.ProductTypeEnum;
import com.softtech.finalproject.service.productcategory.ProductCategoryService;
import com.softtech.finalproject.service.productcategory.ProductCategoryServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;
    @PutMapping("/product-category")
    public ResponseEntity updateTaxRates(ProductTypeEnum productTypeEnum,Double newTaxRates){
        return ResponseEntity.ok(productCategoryService.updateTaxRates(productTypeEnum,newTaxRates));
    }
}
