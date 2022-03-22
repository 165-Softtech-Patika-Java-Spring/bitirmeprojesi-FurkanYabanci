package com.softtech.market.service;


import com.softtech.market.converter.ProductMapper;
import com.softtech.market.dto.ProductDto;
import com.softtech.market.dto.request.ProductSaveRequestDto;
import com.softtech.market.dto.request.VatUpdateRequestDto;
import com.softtech.market.exception.ItemNotFoundException;
import com.softtech.market.model.Product;
import com.softtech.market.model.ProductType;
import com.softtech.market.model.Vat;
import com.softtech.market.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {


     @Mock
     private ProductRepository productRepository;

     @Mock
     private VatService vatService;

     @Mock
     private BaseService<Product> baseService;

     @Spy
     private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldFindAll() {
        Product product = mock(Product.class);
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> productDtoList = productMapper.convertToProductDtoList(productList);
        List<ProductDto> result = productService.findAll();

        assertEquals(productList.get(0).getId(),productDtoList.get(0).getId());
        assertEquals(1, result.size());
    }

    @Test
    void shouldFindAllWhenProductListIsEmpty() {
        List<Product> productList = new ArrayList<>();

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> productDtoList = productMapper.convertToProductDtoList(productList);
        List<ProductDto> result = productService.findAll();

        assertEquals(productList.size(),productDtoList.size());
        assertEquals(0, result.size());
    }

    @Test
    void shouldFindAllByProductType() {
        ProductType productType = ProductType.FOOD;

        Vat vat = mock(Vat.class);
        when(vat.getProductType()).thenReturn(productType);

        Product product = mock(Product.class);
        when(product.getVat()).thenReturn(vat);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findAllByVat_ProductType(productType)).thenReturn(productList);

        List<ProductDto> productDtoList = productMapper.convertToProductDtoList(productList);
        List<ProductDto> result = productService.findAllByProductType(productType);

        assertEquals(productList.get(0).getVat().getProductType(),productDtoList.get(0).getVat().getProductType());
        assertEquals(productType,result.get(0).getVat().getProductType());
    }

    @Test
    void findAllByTaxIncludedPriceBetween() {
        BigDecimal startPrice = BigDecimal.valueOf(1);
        BigDecimal endPrice = BigDecimal.valueOf(2);

        Product product = mock(Product.class);
        when(product.getTaxIncludedPrice()).thenReturn(startPrice);
        when(product.getTaxIncludedPrice()).thenReturn(endPrice);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findAllByTaxIncludedPriceBetween(startPrice,endPrice)).thenReturn(productList);
        List<ProductDto> productDtoList = productMapper.convertToProductDtoList(productList);
        List<ProductDto> result = productService.findAllByTaxIncludedPriceBetween(startPrice,endPrice);

        assertEquals(productList.get(0).getTaxIncludedPrice(),productDtoList.get(0).getTaxIncludedPrice());
        assertEquals(1,result.size());
    }

    @Test
    void findProductDetails() {

    }

    @Test
    void shouldFindById() {
        Long id = 10l;

        Product product = mock(Product.class);
        when(product.getId()).thenReturn(id);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        product = productService.findById(id);

        assertEquals(id, product.getId());
    }

    @Test
    void shouldNotFindByIdWhenIdDoesNotExist() {
        when(productRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> productService.findById(anyLong()));
        verify(productRepository).findById(anyLong());
    }


    @Test
    void shouldSave() {
        ProductSaveRequestDto productSaveRequestDto = new ProductSaveRequestDto();
        productSaveRequestDto.setName("Kalem");
        productSaveRequestDto.setTaxFreePrice(BigDecimal.valueOf(10));
        productSaveRequestDto.setVatId(1l);

        Vat vat = new Vat();
        vat.setId(1l);
        vat.setRate(BigDecimal.valueOf(20));
        vat.setProductType(ProductType.STATIONERY);

        Product product = new Product();
        product.setId(1l);
        product.setName("Kalem");
        product.setTaxFreePrice(BigDecimal.valueOf(10));
        product.setTaxIncludedPrice(BigDecimal.valueOf(12));
        product.setVat(vat);

        when(vatService.findById(1l)).thenReturn(vat);
        when(productRepository.save(any())).thenReturn(product);

        ProductDto result = productService.save(productSaveRequestDto);
        ProductDto productDto = productMapper.convertToProductDto(product);

        assertEquals(result.getId(),productDto.getId());

        verify(vatService).findById(1l);
    }

    @Test
    void update() {

      /*  Product product1 = new Product();
        product1.setId(1l);
        product1.setName("Kalem");
        product1.setTaxFreePrice(BigDecimal.valueOf(10));
        product1.setTaxIncludedPrice(BigDecimal.valueOf(12));


       ProductUpdateRequestDto productUpdateRequestDto = mock(ProductUpdateRequestDto.class);
       Product product = mock(Product.class);
       Vat vat = mock(Vat.class);

       when(productUpdateRequestDto.getId()).thenReturn(1l);;

        when(vatService.findById(anyLong())).thenReturn(vat);
        when(productRepository.save(product1)).thenReturn(product);
        when(productRepository.findById(1l)).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);

        ProductDto result = productService.update(productUpdateRequestDto);
        ProductDto productDto = productMapper.convertToProductDto(product);

        assertEquals(result.getId(),productDto.getId());

        verify(vatService).findById(1l);*/
    }

    @Test
    void delete() {

        Product product = mock(Product.class);

        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(product));

        productService.delete(anyLong());

        verify(productRepository).findById(anyLong());
        verify(productRepository).delete(any());
    }


    @Test
    void findAllByVatId() {

        Product product = mock(Product.class);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findAllByVat_Id(anyLong())).thenReturn(productList);

        List<Product> products = productService.findAllByVatId(anyLong());

        assertEquals(products.size(), productList.size());

    }

    @Test
    void updateVatRateAndTaxIncludedPrice() {
        VatUpdateRequestDto vatUpdateRequestDto = mock(VatUpdateRequestDto.class);
        Product product = mock(Product.class);
        Vat vat = mock(Vat.class);

        when(product.getTaxIncludedPrice()).thenReturn(any());
        when(vatService.findById(anyLong())).thenReturn(vat);
        when(productRepository.save(any())).thenReturn(product);

        product = productService.updateVatRateAndTaxIncludedPrice(vatUpdateRequestDto,product);
        assertEquals(vatUpdateRequestDto.getId(), product.getId());
    }
}