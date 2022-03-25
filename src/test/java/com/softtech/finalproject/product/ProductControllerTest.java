package com.softtech.finalproject.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.softtech.finalproject.BaseIntegrationTest;
import com.softtech.finalproject.dao.ProductDao;
import com.softtech.finalproject.dto.product.ProductDto;
import com.softtech.finalproject.dto.product.UpdateProductDto;
import com.softtech.finalproject.dto.product.UpdateProductPriceDto;
import com.softtech.finalproject.model.ProductEntity;
import com.softtech.finalproject.model.ProductTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ProductControllerTest extends BaseIntegrationTest {
    private static final String BASE_PATH = "/api/v1";
    private static final String Base_Path2="/api/v1/products";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    ProductDao productDao;
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }
    @Test
    @Sql(scripts = "/product-category-create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_create_product() throws Exception{
        ProductDto productDto=new ProductDto();
        productDto.setProductName("new product");
        productDto.setProductType(ProductTypeEnum.FOOD);
        productDto.setTaxFreeSellingPrice(new BigDecimal(10.0));
        String content = objectMapper.writeValueAsString(productDto);
        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/products").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);

        Optional<ProductEntity> entity = productDao.findById(1L);
        assertThat(entity).isNotEmpty();
    }
    @Test
    @Sql(scripts = "/product-category-create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_not_create_TaxFreeSellingPrice_zero() throws Exception{
        ProductDto productDto=new ProductDto();
        productDto.setProductName("new product");
        productDto.setProductType(ProductTypeEnum.FOOD);
        productDto.setTaxFreeSellingPrice(new BigDecimal(0));
        String content = objectMapper.writeValueAsString(productDto);
        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/products").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();
        boolean isSuccess = isSuccess(result);
        assertFalse(isSuccess);
    }

    @Test
    @Sql(scripts = {"/product-category-create.sql", "/create-product.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_retrieve_all_products_by_product_type() throws Exception{
        MvcResult result = mockMvc.perform(
                get(BASE_PATH+"/getallbyproducttype?productTypeEnum=FOOD").content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }
    @Test
    @Sql(scripts = {"/product-category-create.sql", "/create-product.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_retrieve_all_products_by_between_two_prices() throws Exception{
        MvcResult result = mockMvc.perform(
                get(BASE_PATH+"/getbetweenprices?startPrice=1&endPrice=100").content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }

    @Test
    @Sql(scripts = {"/product-category-create.sql", "/create-product.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_retrieve_all_products() throws Exception{
        MvcResult result = mockMvc.perform(
                get(BASE_PATH+"/productsList").content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }
    @Test
    @Sql(scripts = {"/product-category-create.sql", "/create-product.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_retrieve_information_about_given_category() throws Exception{
        MvcResult result = mockMvc.perform(
                get(BASE_PATH+"/getallinformation?productTypeEnum=FOOD").content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }

    @Test
    @Sql(scripts = {"/product-category-create.sql", "/create-product.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_update_product() throws Exception{
        UpdateProductDto updateProductDto= UpdateProductDto.builder()
                .id(1001L)
                .productName("new")
                .productType(ProductTypeEnum.TECHNOLOGY)
                .taxFreeSellingPrice(new BigDecimal(15))
                .build();
        String content = objectMapper.writeValueAsString(updateProductDto);
        MvcResult result = mockMvc.perform(
                put(BASE_PATH+"/products").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Sql(scripts = {"/product-category-create.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_not_update_product_when_id_not_exist() throws Exception{
        UpdateProductDto updateProductDto= UpdateProductDto.builder()
                .id(1001L)
                .productName("new")
                .productType(ProductTypeEnum.TECHNOLOGY)
                .taxFreeSellingPrice(new BigDecimal(15))
                .build();
        String content = objectMapper.writeValueAsString(updateProductDto);
        MvcResult result = mockMvc.perform(
                put(BASE_PATH+"/products").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();
    }
    @Test
    @Sql(scripts = {"/product-category-create.sql", "/create-product.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_update_product_price() throws Exception{
        UpdateProductPriceDto updateProductPriceDto=new UpdateProductPriceDto();
        updateProductPriceDto.setId(1001L);
        updateProductPriceDto.setNewTaxFreePrice(new BigDecimal(15));
        String content = objectMapper.writeValueAsString(updateProductPriceDto);
        MvcResult result = mockMvc.perform(
                put(BASE_PATH+"/updateproductprice").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

    }
    @Test
    @Sql(scripts = {"/product-category-create.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clean-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_not_update_product_price_when_id_not_exist() throws Exception{
        UpdateProductPriceDto updateProductPriceDto=new UpdateProductPriceDto();
        updateProductPriceDto.setId(1001L);
        updateProductPriceDto.setNewTaxFreePrice(new BigDecimal(15));
        String content = objectMapper.writeValueAsString(updateProductPriceDto);
        MvcResult result = mockMvc.perform(
                put(BASE_PATH+"/updateproductprice").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();
    }
}
