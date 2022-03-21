package com.softtech.market.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(precision = 19, scale = 2, nullable = false)
    @Min(value = 1)
    private BigDecimal taxFreePrice;

    @Column(precision = 19, scale = 2)
    private BigDecimal taxIncludedPrice;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Vat vat;
}
