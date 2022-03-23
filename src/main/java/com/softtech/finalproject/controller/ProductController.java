package com.softtech.finalproject.controller;

import com.softtech.finalproject.dto.ProductDto;
import com.softtech.finalproject.dto.UpdateProductDto;
import com.softtech.finalproject.dto.UpdateProductPriceDto;
import com.softtech.finalproject.gen.dto.RestResponse;
import com.softtech.finalproject.model.ProductTypeEnum;
import com.softtech.finalproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {
    private  final ProductService productService;
    @PostMapping("/products")
    public ResponseEntity createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(RestResponse.of(productService.createProduct(productDto)));
    }
    @GetMapping("/productsList")
    public ResponseEntity getProductsList(){
        return ResponseEntity.ok(RestResponse.of(productService.getProductsList()));
    }
    @DeleteMapping("/{id}")
    public void delete(Long id){

        productService.deleteProduct(id);
    }
    @PutMapping("/products")
    public ResponseEntity updateProduct(@RequestBody UpdateProductDto updateProductDto){
        return ResponseEntity.ok(RestResponse.of(productService.updateProduct(updateProductDto)));
    }
    @PutMapping("/updateproductprice")
    public ResponseEntity updateProductPrice(@RequestBody UpdateProductPriceDto updateProductDto){
        return ResponseEntity.ok(RestResponse.of(productService.updateProductPrice(updateProductDto)));
    }
    @GetMapping("/getallproducttype")
    public ResponseEntity getAllProductsByProductType(@RequestParam ProductTypeEnum productTypeEnum){
        return ResponseEntity.ok(RestResponse.of(productService.getAllByProductType(productTypeEnum)));
    }
    @GetMapping
    public ResponseEntity findAllBetweenPrices(@RequestParam BigDecimal startPrice,@RequestParam BigDecimal endPrice){
        return ResponseEntity.ok(RestResponse.of(productService.findAllByProductPriceBetween(startPrice, endPrice)));
    }

}
