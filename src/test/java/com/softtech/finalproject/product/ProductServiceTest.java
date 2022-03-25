package com.softtech.finalproject.product;

import com.softtech.finalproject.converter.ProductConverter;
import com.softtech.finalproject.dto.product.ProductDto;
import com.softtech.finalproject.dto.product.ProductResponse;
import com.softtech.finalproject.dto.product.UpdateProductDto;
import com.softtech.finalproject.dto.product.UpdateProductPriceDto;
import com.softtech.finalproject.gen.exceptions.GenBusinessException;
import com.softtech.finalproject.gen.exceptions.ItemNotFoundException;
import com.softtech.finalproject.model.ProductCategory;
import com.softtech.finalproject.model.ProductEntity;
import com.softtech.finalproject.model.ProductTypeEnum;
import com.softtech.finalproject.service.EntityService.ProductCategoryEntityService;
import com.softtech.finalproject.service.EntityService.ProductEntityService;
import com.softtech.finalproject.service.product.ProductServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    ProductServiceImp productServiceImp;
    @Mock
    ProductConverter productConverter;

    @Mock
    ProductEntityService productEntityService;
    @Mock
    ProductCategoryEntityService productCategoryEntityService;
    @BeforeEach
    void setup(){
        productServiceImp=new ProductServiceImp(productConverter,productEntityService,productCategoryEntityService);
    }
    @Test
    void should_create_product(){
        //given
        ProductDto productDto=new ProductDto();
        productDto.setProductName("apple");
        productDto.setProductType(ProductTypeEnum.FOOD);
        productDto.setTaxFreeSellingPrice(new BigDecimal(5));
        //mock
        ProductResponse productResponse=ProductResponse.builder()
                .id(1L)
                .productName("elma")
                .productType(ProductTypeEnum.FOOD)
                .taxFreeSellingPrice(new BigDecimal(5))
                .productPrice(new BigDecimal(5.05))
                .build();
        ProductCategory productCategory=new ProductCategory();
        productCategory.setId(1L);
        productCategory.setTaxRates(0.01);
        productCategory.setProductType(ProductTypeEnum.FOOD);

        ProductEntity productEntity=new ProductEntity();
        productEntity.setId(1L);
        productEntity.setProductName("apple");
        productEntity.setTaxFreeSellingPrice(new BigDecimal(5));
        productEntity.setProductCategory(productCategory);
        productEntity.setProductPrice(new BigDecimal(5.05));

        when(productCategoryEntityService.findProductCategoryByProductType(productDto.getProductType())).thenReturn(productCategory);
        when(productConverter.convertToProduct(any(),any(),any())).thenReturn(productEntity);
        when(productEntityService.save(productEntity)).thenReturn(productEntity);
        when(productConverter.convertToProductResponse(any())).thenReturn(productResponse);
        //when
        ProductResponse response = productServiceImp.createProduct(productDto);
        //then
        assertThat(response).isNotNull();
    }
    @Test
    void should_not_create_product_when_product_name_empty(){
        ProductDto productDto=new ProductDto();
        productDto.setProductName("");
        productDto.setProductType(ProductTypeEnum.FOOD);
        productDto.setTaxFreeSellingPrice(new BigDecimal(5));

        Throwable throwable=catchThrowable(()->productServiceImp.createProduct(productDto));
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(GenBusinessException.class);
    }

    @Test
    void should_not_create_product_when_product_type_empty(){
        ProductDto productDto=new ProductDto();
        productDto.setProductName("apple");
        productDto.setProductType(null);
        productDto.setTaxFreeSellingPrice(new BigDecimal(5));

        Throwable throwable=catchThrowable(()->productServiceImp.createProduct(productDto));
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(GenBusinessException.class);
    }

    @Test
    void should_not_create_product_when_TaxFreeSellingPrice_zero(){
        ProductDto productDto=new ProductDto();
        productDto.setProductName("apple");
        productDto.setProductType(ProductTypeEnum.FOOD);
        productDto.setTaxFreeSellingPrice(new BigDecimal(0));

        Throwable throwable=catchThrowable(()->productServiceImp.createProduct(productDto));
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(GenBusinessException.class);
    }

    @Test
    void should_retrieve_all_products_by_product_type(){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setId(1L);
        productCategory.setTaxRates(0.01);
        productCategory.setProductType(ProductTypeEnum.FOOD);

        List<ProductEntity> productEntityList = new ArrayList<>();
        ProductEntity productEntity=new ProductEntity();
        productEntity.setId(1L);
        productEntity.setProductName("apple");
        productEntity.setTaxFreeSellingPrice(new BigDecimal(5));
        productEntity.setProductCategory(productCategory);
        productEntity.setProductPrice(new BigDecimal(5.05));
        productEntityList.add(productEntity);

        when(productEntityService.findAllByProductType(any())).thenReturn(productEntityList);
        //when
        List<ProductResponse> productResponseList = productServiceImp.getAllByProductType(productCategory.getProductType());
        //then
        assertThat(productResponseList).isNotNull();
        assertThat(productResponseList.size()).isEqualTo(1);
    }
    @Test
    void should_not_delete_product_when_id_does_not_exist(){
        Long id=1L;
        when(productEntityService.getByIdWithControl(1L)).thenThrow(ItemNotFoundException.class);
        Throwable throwable=catchThrowable(() ->productServiceImp.deleteProduct(anyLong()));
        verify(productEntityService).getByIdWithControl(anyLong());
        assertThat(throwable)
                .isNotNull();
    }
    @Test
    void should_delete_product(){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setId(1L);
        productCategory.setTaxRates(0.01);
        productCategory.setProductType(ProductTypeEnum.FOOD);

        ProductEntity productEntity=new ProductEntity();
        productEntity.setId(1L);
        productEntity.setProductName("apple");
        productEntity.setProductCategory(productCategory);
        productEntity.setTaxFreeSellingPrice(new BigDecimal(5));
        productEntity.setProductPrice(new BigDecimal(5));
        when(productEntityService.getByIdWithControl(productEntity.getId())).thenReturn(productEntity);
        productServiceImp.deleteProduct(productEntity.getId());
        verify(productEntityService).getByIdWithControl(productEntity.getId());
    }
    @Test
    void should_update_product(){
        ProductResponse productResponse=ProductResponse.builder()
                .id(1L)
                .productName("elma")
                .productType(ProductTypeEnum.FOOD)
                .taxFreeSellingPrice(new BigDecimal(5))
                .productPrice(new BigDecimal(5.05))
                .build();
        ProductCategory productCategory=new ProductCategory();
        productCategory.setId(1L);
        productCategory.setTaxRates(0.01);
        productCategory.setProductType(ProductTypeEnum.FOOD);

        UpdateProductDto updateProductDto=UpdateProductDto.builder()
                                .id(1L)
                                .productType(ProductTypeEnum.FOOD)
                                .productName("elma")
                                .taxFreeSellingPrice(new BigDecimal(5))
                                .build();
        ProductEntity productEntity=new ProductEntity();
        productEntity.setId(1L);
        productEntity.setProductName("elma");
        productEntity.setProductCategory(productCategory);
        productEntity.setTaxFreeSellingPrice(new BigDecimal(5));
        productEntity.setProductPrice(new BigDecimal(5.05));
        when(productCategoryEntityService.findProductCategoryByProductType(any())).thenReturn(productCategory);
        when(productEntityService.existsById(productEntity.getId())).thenReturn(Boolean.TRUE);
        when(productEntityService.save(productEntity)).thenReturn(productEntity);
        when(productConverter.convertToProductfromUpdateDto(any(),any(),any())).thenReturn(productEntity);
        when(productConverter.convertToProductResponse(any())).thenReturn(productResponse);
        ProductResponse response = productServiceImp.updateProduct(updateProductDto);
        assertThat(response)
                .isNotNull();
        assertThat(response.getProductPrice()).isEqualTo(productEntity.getProductPrice());
        assertThat(response.getProductName()).isEqualTo("elma");
        assertThat(response.getProductType()).isEqualTo(ProductTypeEnum.FOOD);
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    void should_not_update_product_when_id_is_not_exist(){
        UpdateProductDto updateProductDto=mock(UpdateProductDto.class);
        when(productEntityService.existsById(anyLong())).thenThrow(ItemNotFoundException.class);
        Throwable throwable=catchThrowable(() -> productServiceImp.updateProduct(updateProductDto));
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(ItemNotFoundException.class);
    }
    @Test
    void should_update_price_of_product(){
        //given
        UpdateProductPriceDto dto= new UpdateProductPriceDto();
        dto.setId(1L);
        dto.setNewTaxFreePrice(new BigDecimal(5));
        //mock
        ProductResponse productResponse=ProductResponse.builder()
                .id(1L)
                .productName("elma")
                .productType(ProductTypeEnum.FOOD)
                .taxFreeSellingPrice(new BigDecimal(5))
                .productPrice(new BigDecimal(5.05))
                .build();

        ProductCategory productCategory=new ProductCategory();
        productCategory.setId(1L);
        productCategory.setTaxRates(0.01);
        productCategory.setProductType(ProductTypeEnum.FOOD);

        ProductEntity productEntity=new ProductEntity();
        productEntity.setId(1L);
        productEntity.setProductName("elma");
        productEntity.setProductCategory(productCategory);
        productEntity.setTaxFreeSellingPrice(new BigDecimal(5));
        productEntity.setProductPrice(new BigDecimal(5.05));

        when(productEntityService.getByIdWithControl(dto.getId())).thenReturn(productEntity);
        when(productEntityService.save(any())).thenReturn(productEntity);
        when(productConverter.convertToProductResponse(any())).thenReturn(productResponse);
        //when
        ProductResponse response = productServiceImp.updateProductPrice(dto);
        //then
        assertThat(response).isNotNull();
    }
    @Test
    void should_not_update_products_price(){
        UpdateProductPriceDto updateProductDto=mock(UpdateProductPriceDto.class);
        when(productEntityService.getByIdWithControl(anyLong())).thenThrow(ItemNotFoundException.class);
        Throwable throwable=catchThrowable(() -> productServiceImp.updateProductPrice(updateProductDto));
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(ItemNotFoundException.class);
    }
}
