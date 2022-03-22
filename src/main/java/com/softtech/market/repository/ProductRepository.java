package com.softtech.market.repository;

import com.softtech.market.dto.ProductDetailsDto;
import com.softtech.market.model.Product;
import com.softtech.market.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByVat_Id(long id);
    List<Product> findAllByVat_ProductType(ProductType productType);
    List<Product> findAllByTaxIncludedPriceBetween(BigDecimal startPrice, BigDecimal endPrice);

    @Query(value = "select new com.softtech.market.dto.ProductDetailsDto( "+
            "v.productType, "+
            "v.rate,"+
            "MIN (p.taxIncludedPrice),"+
            "MAX (p.taxIncludedPrice),"+
            "AVG (p.taxIncludedPrice),"+
            "count(p)" +
            ") "+
            "FROM Product p LEFT JOIN Vat v ON v.id=p.vat.id GROUP BY v.productType,v.rate"
    )
    List<ProductDetailsDto> findProductDetails();
}
