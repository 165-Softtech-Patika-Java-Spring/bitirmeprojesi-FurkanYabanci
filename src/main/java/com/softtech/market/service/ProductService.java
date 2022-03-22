package com.softtech.market.service;

import com.softtech.market.converter.ProductMapper;
import com.softtech.market.dto.ProductDto;
import com.softtech.market.dto.request.ProductSaveRequestDto;
import com.softtech.market.dto.request.ProductUpdateRequestDto;
import com.softtech.market.dto.request.VatUpdateRequestDto;
import com.softtech.market.enums.ProductErrorMessage;
import com.softtech.market.exception.ItemNotFoundException;
import com.softtech.market.model.BaseAdditionalFields;
import com.softtech.market.model.Product;
import com.softtech.market.model.ProductType;
import com.softtech.market.model.Vat;
import com.softtech.market.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final VatService vatService;
    private final BaseService<Product> baseService;

    public List<ProductDto> findAll(){
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = ProductMapper.INSTANCE.convertToProductDtoList(products);
        return productDtoList;
    }

    public List<ProductDto> findAllByProductType(ProductType productType){
        List<Product> products = productRepository.findAllByVat_ProductType(productType);
        List<ProductDto> productDtoList = ProductMapper.INSTANCE.convertToProductDtoList(products);
        return productDtoList;
    }

    public List<ProductDto> findAllByTaxIncludedPriceBetween(BigDecimal startPrice, BigDecimal endPrice){
        List<Product> products = productRepository.findAllByTaxIncludedPriceBetween(startPrice,endPrice);
        List<ProductDto> productDtoList = ProductMapper.INSTANCE.convertToProductDtoList(products);
        return productDtoList;
    }

    public List<?> findProductDetails(){
        return productRepository.findProductDetails();
    }

    public ProductDto save(ProductSaveRequestDto productSaveRequestDto){
        Product product = ProductMapper.INSTANCE.convertToProduct(productSaveRequestDto);
        Vat vat = vatService.findById(productSaveRequestDto.getVatId());
        BigDecimal taxIncludedPrice = product.getTaxFreePrice().multiply(vat.getRate())
                .divide(BigDecimal.valueOf(100)).add(product.getTaxFreePrice());
        product.setTaxIncludedPrice(taxIncludedPrice);
        product.setVat(vat);
        baseService.addBaseAdditionalFields(product);
        product = productRepository.save(product);
        ProductDto productDto = ProductMapper.INSTANCE.convertToProductDto(product);
        return productDto;
    }

    public ProductDto update(ProductUpdateRequestDto productUpdateRequestDto){
        Product product = findById(productUpdateRequestDto.getId());
        product.setName(productUpdateRequestDto.getName());
        product.setTaxFreePrice(productUpdateRequestDto.getTaxFreePrice());
        Vat vat = vatService.findById(productUpdateRequestDto.getVatId());
        product.setVat(vat);
        BigDecimal taxIncludedPrice = productUpdateRequestDto.getTaxFreePrice().multiply(vat.getRate())
                .divide(BigDecimal.valueOf(100)).add(product.getTaxFreePrice());
        product.setTaxIncludedPrice(taxIncludedPrice);
        baseService.addBaseAdditionalFields(product);
        product = productRepository.save(product);
        ProductDto productDto = ProductMapper.INSTANCE.convertToProductDto(product);
        return productDto;
    }

    public void delete(long id){
        Product product = findById(id);
        productRepository.delete(product);
    }

    protected Product findById(long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(ProductErrorMessage.PRODUCT_NOT_FOUND));
        return product;
    }

    protected List<Product> findAllByVatId(long id){
        List<Product> products = productRepository.findAllByVat_Id(id);
        return products;
    }

    @Transactional
    protected Product updateVatRateAndTaxIncludedPrice(VatUpdateRequestDto vatUpdateRequestDto, Product product){
        BigDecimal taxIncludedPrice = product.getTaxFreePrice().multiply(vatUpdateRequestDto.getRate())
                .divide(BigDecimal.valueOf(100)).add(product.getTaxFreePrice());
        product.setTaxIncludedPrice(taxIncludedPrice);
        Vat vat = vatService.findById(vatUpdateRequestDto.getId());
        product.setVat(vat);
        product = productRepository.save(product);
        return product;
    }

}
