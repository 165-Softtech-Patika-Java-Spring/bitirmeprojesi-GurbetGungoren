package com.softtech.finalproject.controller;

import com.softtech.finalproject.dto.product.ProductDto;
import com.softtech.finalproject.dto.product.UpdateProductDto;
import com.softtech.finalproject.dto.product.UpdateProductPriceDto;
import com.softtech.finalproject.gen.dto.RestResponse;
import com.softtech.finalproject.model.ProductTypeEnum;
import com.softtech.finalproject.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {
    private  final ProductService productService;
    @Operation(tags = "Product Controller")
    @PostMapping("/products")
    public ResponseEntity createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(RestResponse.of(productService.createProduct(productDto)));
    }
    @Operation(tags = "Product Controller")
    @GetMapping("/productsList")
    public ResponseEntity getProductsList(){
        return ResponseEntity.ok(RestResponse.of(productService.getProductsList()));
    }
    @Operation(tags = "Product Controller")
    @DeleteMapping("products/{id}")
    public void delete(Long id){
        productService.deleteProduct(id);
    }
    @Operation(tags = "Product Controller")
    @PutMapping("/products")
    public ResponseEntity updateProduct(@RequestBody UpdateProductDto updateProductDto){
        return ResponseEntity.ok(RestResponse.of(productService.updateProduct(updateProductDto)));
    }
    @Operation(tags = "Product Controller")
    @PutMapping("/updateproductprice")
    public ResponseEntity updateProductPrice(@RequestBody UpdateProductPriceDto updateProductDto){
        return ResponseEntity.ok(RestResponse.of(productService.updateProductPrice(updateProductDto)));
    }
    @Operation(tags = "Product Controller")
    @GetMapping("/getallbyproducttype")
    public ResponseEntity getAllProductsByProductType(@RequestParam ProductTypeEnum productTypeEnum){
        return ResponseEntity.ok(RestResponse.of(productService.getAllByProductType(productTypeEnum)));
    }
    @Operation(tags = "Product Controller")
    @GetMapping("/getbetweenprices")
    public ResponseEntity findAllBetweenPrices(@RequestParam BigDecimal startPrice,@RequestParam BigDecimal endPrice){
        return ResponseEntity.ok(RestResponse.of(productService.findAllByProductPriceBetween(startPrice, endPrice)));
    }
    @Operation(tags = "Product Controller")
    @GetMapping("/getallinformation")
    public ResponseEntity gelAllCategoryInformation(ProductTypeEnum productTypeEnum){
        return ResponseEntity.ok(RestResponse.of(productService.getAllCategoryDetails(productTypeEnum)));
    }
}
