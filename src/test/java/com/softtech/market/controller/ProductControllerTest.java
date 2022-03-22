package com.softtech.market.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.softtech.market.dto.request.ProductSaveRequestDto;
import com.softtech.market.dto.request.ProductUpdateRequestDto;
import com.softtech.market.model.Product;
import com.softtech.market.model.ProductType;
import com.softtech.market.model.Vat;
import com.softtech.market.repository.ProductRepository;
import com.softtech.market.repository.VatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductControllerTest extends BaseControllerTest{

    private static final String BASE_PATH = "/api/v1/products";

    private MockMvc mockMvc;

    @Autowired
    private VatRepository vatRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void findAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(BASE_PATH)
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }

    @Test
    void findAllByProductType() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(BASE_PATH+"/findAll/STATIONERY")
                .content(ProductType.STATIONERY.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }

    @Test
    void findAllByTaxIncludedPriceBetween() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_PATH+"/taxIncludedPrice?startPrice=9&endPrice=13")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }

    @Test
    void findProductDetails() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(BASE_PATH+"/productDetails")
                .content("")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }

    @Test
    void save() throws Exception {

        Vat vat = new Vat();
        vat.setId(1l);
        vat.setProductType(ProductType.STATIONERY);
        vat.setRate(BigDecimal.valueOf(20));
        vatRepository.save(vat);

        ProductSaveRequestDto productSaveRequestDto = new ProductSaveRequestDto();
        productSaveRequestDto.setName("Kitap");
        productSaveRequestDto.setTaxFreePrice(BigDecimal.valueOf(11));
        productSaveRequestDto.setVatId(1l);

        String content = objectMapper.writeValueAsString(productSaveRequestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_PATH)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);

    }

    @Test
    void update() throws Exception {

        Vat vat = new Vat();
        vat.setId(1l);
        vat.setProductType(ProductType.FOOD);
        vat.setRate(BigDecimal.valueOf(20));
        vatRepository.save(vat);

        Product product = new Product();
        product.setId(1l);
        product.setName("Ekmek");
        product.setTaxFreePrice(BigDecimal.valueOf(10));
        product.setTaxIncludedPrice(BigDecimal.valueOf(12));
        product.setVat(vat);
        productRepository.save(product);

        ProductUpdateRequestDto productUpdateRequestDto = new ProductUpdateRequestDto();
        productUpdateRequestDto.setId(1l);
        productUpdateRequestDto.setName("Peynir");
        productUpdateRequestDto.setTaxFreePrice(BigDecimal.valueOf(15));
        productUpdateRequestDto.setVatId(1l);

        String content = objectMapper.writeValueAsString(productUpdateRequestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .patch(BASE_PATH)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);

    }

    @Test
    void delete() throws Exception {
        Vat vat = new Vat();
        vat.setId(1l);
        vat.setProductType(ProductType.STATIONERY);
        vat.setRate(BigDecimal.valueOf(20));
        vatRepository.save(vat);

        Product product = new Product();
        product.setId(1l);
        product.setName("Kalem");
        product.setTaxFreePrice(BigDecimal.valueOf(10));
        product.setTaxIncludedPrice(BigDecimal.valueOf(12));
        product.setVat(vat);
        productRepository.save(product);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete(BASE_PATH+"/1")
                .content("1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }
}