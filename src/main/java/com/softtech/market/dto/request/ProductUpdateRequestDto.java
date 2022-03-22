package com.softtech.market.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ProductUpdateRequestDto {

    private Long id;
    private String name;
    private BigDecimal taxFreePrice;
    private long vatId;
}
