package com.softtech.market.dto.request;

import com.softtech.market.dto.VatDto;
import com.softtech.market.model.Vat;
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
