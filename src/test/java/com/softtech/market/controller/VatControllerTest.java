package com.softtech.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.softtech.market.dto.request.VatSaveRequestDto;
import com.softtech.market.dto.request.VatUpdateRequestDto;
import com.softtech.market.model.ProductType;
import com.softtech.market.model.Vat;
import com.softtech.market.repository.VatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class VatControllerTest extends BaseControllerTest{

    private static final String BASE_PATH = "/api/v1/vats";

    private MockMvc mockMvc;

    @Autowired
    private VatRepository vatRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void save() throws Exception {

        VatSaveRequestDto vatSaveRequestDto = new VatSaveRequestDto();
        vatSaveRequestDto.setRate(BigDecimal.valueOf(20));
        vatSaveRequestDto.setProductType(ProductType.STATIONERY);

        String content = objectMapper.writeValueAsString(vatSaveRequestDto);

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
        /*Vat vat = new Vat();
        vat.setId(1l);
        vat.setProductType(ProductType.FOOD);
        vat.setRate(BigDecimal.valueOf(20));
        vatRepository.save(vat);

        VatUpdateRequestDto vatUpdateRequestDto = new VatUpdateRequestDto();
        vatUpdateRequestDto.setId(1l);
        vatUpdateRequestDto.setRate(BigDecimal.valueOf(25));
        vatUpdateRequestDto.setProductType(ProductType.FOOD);

        String content = objectMapper.writeValueAsString(vatUpdateRequestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .patch(BASE_PATH)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);*/

    }

    @Test
    void delete() throws Exception {

        Vat vat = new Vat();
        vat.setId(1l);
        vat.setProductType(ProductType.STATIONERY);
        vat.setRate(BigDecimal.valueOf(20));
        vatRepository.save(vat);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete(BASE_PATH+"/1")
                .content("1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }
}