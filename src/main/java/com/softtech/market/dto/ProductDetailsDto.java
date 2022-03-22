package com.softtech.market.dto;

import com.softtech.market.model.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDetailsDto {
    ProductType productType;
    BigDecimal rate;
    BigDecimal minPrice;
    BigDecimal maxPrice;
    Double avgPrice;
    long countProduct;
}
