package com.softtech.market.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
public class ProductSaveRequestDto {

    private String name;
    private BigDecimal taxFreePrice;
    private Long vatId;
}
