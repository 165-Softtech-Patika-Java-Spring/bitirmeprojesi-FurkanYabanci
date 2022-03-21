package com.softtech.market.dto.request;

import com.softtech.market.dto.VatDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ProductSaveRequestDto {

    private String name;
    private BigDecimal taxFreePrice;
    private Long vatId;
}
