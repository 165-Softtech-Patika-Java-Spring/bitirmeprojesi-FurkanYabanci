package com.softtech.market.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softtech.market.dto.GeneralResponseDto;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

public class BaseControllerTest {

    protected ObjectMapper objectMapper;

    protected boolean isSuccess(MvcResult result) throws JsonProcessingException, UnsupportedEncodingException {
        GeneralResponseDto response = getGeneralResponse(result);
        boolean isSuccess = isSuccess(response);
        return isSuccess;
    }

    private GeneralResponseDto getGeneralResponse(MvcResult result) throws JsonProcessingException, UnsupportedEncodingException {
        GeneralResponseDto response = objectMapper.readValue(result.getResponse().getContentAsString(), GeneralResponseDto.class);
        return response;
    }

    private boolean isSuccess(GeneralResponseDto response) {
        boolean isSuccess = response.isSuccess();
        return isSuccess;
    }


}
