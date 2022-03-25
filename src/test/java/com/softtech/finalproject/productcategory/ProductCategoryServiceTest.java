package com.softtech.finalproject.productcategory;
import com.softtech.finalproject.model.ProductCategory;
import com.softtech.finalproject.model.ProductTypeEnum;
import com.softtech.finalproject.service.EntityService.ProductCategoryEntityService;
import com.softtech.finalproject.service.EntityService.ProductEntityService;
import com.softtech.finalproject.service.productcategory.ProductCategoryService;
import com.softtech.finalproject.service.productcategory.ProductCategoryServiceImp;
import com.softtech.finalproject.service.user.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ProductCategoryServiceTest {

    ProductCategoryService productCategoryService;
    @Mock
    ProductCategoryEntityService productCategoryEntityService;
    @Mock
    ProductEntityService productEntityService;
    @BeforeEach
    void setup(){
        productCategoryService=new ProductCategoryServiceImp(
                productCategoryEntityService,productEntityService
        );
    }
    @Test
    void should_change_tax_rates_and_update_products_selling_price(){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setId(1001L);
        productCategory.setTaxRates(0.01);
        productCategory.setProductType(ProductTypeEnum.FOOD);
        ProductTypeEnum typeEnum=ProductTypeEnum.FOOD;
        Double newTaxRates=2.0;
        when(productCategoryEntityService.findProductCategoryByProductType(any())).thenReturn(productCategory);
        when(productCategoryEntityService.findProductCategoryByProductType(any())).thenReturn(productCategory);
        doNothing().when(productEntityService).saveAll(any());
        Long id = productCategoryService.updateTaxRates(typeEnum, newTaxRates);

    }
}
