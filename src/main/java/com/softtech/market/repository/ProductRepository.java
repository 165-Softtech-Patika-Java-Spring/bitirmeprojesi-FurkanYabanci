package com.softtech.market.repository;

import com.softtech.market.model.Product;
import com.softtech.market.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByVat_Id(long id);
    List<Product> findAllByVat_ProductType(ProductType productType);
    List<Product> findAllByTaxIncludedPriceBetween(BigDecimal startPrice, BigDecimal endPrice);

   /* @Query(value = "select "+
            "p.vat.productType, "+
            "p.vat.rate,"+
            "MIN (p.taxIncludedPrice) AS MinPrice,"+
            "MAX (p.taxIncludedPrice) AS MaxPrice,"+
            "AVG (p.taxIncludedPrice) AS AveragePrice,"+
            "count (p) AS ProductCount "+
            "FROM Product p group by p.vat.productType"
    )
    List<Product> findProductDetailsByProductType();*/
}
