package com.softtech.market.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class  ProductDto {

    private Long id;
    private String name;
    private BigDecimal taxFreePrice;
    private BigDecimal taxIncludedPrice;
    private VatDto vat;
}
