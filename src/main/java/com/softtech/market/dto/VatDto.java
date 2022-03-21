package com.softtech.market.dto;

import com.softtech.market.model.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class VatDto {

    private Long id;
    private ProductType productType;
    private BigDecimal rate;
}
