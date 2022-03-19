package com.softtech.finalproject.service;

import com.softtech.finalproject.FinalProjectApplication;
import com.softtech.finalproject.model.ProductTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import static com.softtech.finalproject.model.ProductTypeEnum.*;

@Service
public class SellingPriceStrategy{
    private static Map<ProductTypeEnum, ProductType> operationMap= new LinkedHashMap<>();
    static {
        operationMap.put(FOOD, new Food());
        operationMap.put(CLEANING,new Cleaning());
        operationMap.put(TECHNOLOGY,new Technology());
        operationMap.put(OTHER,new Other());
        operationMap.put(CLOTHING,new Clothing());
        operationMap.put(STATIONARY,new Stationary());
    }
    private ProductType getOperation(ProductTypeEnum productTypeEnum) {
        return operationMap.get(productTypeEnum);
    }
    public BigDecimal calculateSellingPrice(ProductTypeEnum productTypeEnum, BigDecimal taxFreePrice) {
        return getOperation(productTypeEnum).apply(taxFreePrice,1.0);
    }
}
