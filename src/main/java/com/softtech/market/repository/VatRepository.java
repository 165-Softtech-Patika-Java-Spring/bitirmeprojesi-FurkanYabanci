package com.softtech.market.repository;

import com.softtech.market.model.ProductType;
import com.softtech.market.model.Vat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface VatRepository extends JpaRepository<Vat, Long> {

    Optional<BigDecimal> findRateByProductType(ProductType type);
}
