package com.softtech.finalproject.controller;

import com.softtech.finalproject.gen.dto.RestResponse;
import com.softtech.finalproject.model.ProductTypeEnum;
import com.softtech.finalproject.service.productcategory.ProductCategoryService;
import com.softtech.finalproject.service.productcategory.ProductCategoryServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;
    @PutMapping("/product-category")
    public ResponseEntity updateTaxRates(@RequestParam ProductTypeEnum productTypeEnum,@RequestParam Double newTaxRates){
        return ResponseEntity.ok(
                RestResponse.of(productCategoryService.updateTaxRates(productTypeEnum,newTaxRates)));
    }
}
