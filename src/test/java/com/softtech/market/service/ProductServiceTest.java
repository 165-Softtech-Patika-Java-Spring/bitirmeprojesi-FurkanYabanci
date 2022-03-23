package com.softtech.market.service;


import com.softtech.market.dto.ProductDetailsDto;
import com.softtech.market.dto.ProductDto;
import com.softtech.market.dto.request.ProductSaveRequestDto;
import com.softtech.market.dto.request.ProductUpdateRequestDto;
import com.softtech.market.dto.request.VatUpdateRequestDto;
import com.softtech.market.exception.ItemNotFoundException;
import com.softtech.market.model.Product;
import com.softtech.market.model.ProductType;
import com.softtech.market.model.Vat;
import com.softtech.market.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    @InjectMocks
    private ProductService productService;

    @Test
    void shouldFindAll() {
        Product product = mock(Product.class);
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> result = productService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldFindAllWhenProductListIsEmpty() {
        List<Product> productList = new ArrayList<>();

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> result = productService.findAll();

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

        List<ProductDto> result = productService.findAllByProductType(productType);

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
        List<ProductDto> result = productService.findAllByTaxIncludedPriceBetween(startPrice,endPrice);

        assertEquals(1,result.size());
    }

    @Test
    void findProductDetails() {
        ProductDetailsDto productDetailsDto = Mockito.mock(ProductDetailsDto.class);
        List<ProductDetailsDto> productDetailsDtoList = new ArrayList<>();
        productDetailsDtoList.add(productDetailsDto);

        when(productRepository.findProductDetails()).thenReturn(productDetailsDtoList);

        List<ProductDetailsDto> result = productService.findProductDetails();

        assertEquals(result.size(), productDetailsDtoList.size());

    }

    @Test
    void shouldFindById() {
        Product product = mock(Product.class);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product result = productService.findById(anyLong());

        assertEquals(result.getId(), product.getId());
    }

    @Test
    void shouldNotFindByIdWhenIdDoesNotExist() {
        when(productRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> productService.findById(anyLong()));
        verify(productRepository).findById(anyLong());
    }

    @Test
    void shouldSave() {

        ProductSaveRequestDto productSaveRequestDto = Mockito.mock(ProductSaveRequestDto.class);
        Vat vat = Mockito.mock(Vat.class);
        Product product = Mockito.mock(Product.class);

        when(vatService.findById(anyLong())).thenReturn(vat);
        when(productSaveRequestDto.getTaxFreePrice()).thenReturn(BigDecimal.valueOf(1));
        when(vat.getRate()).thenReturn(BigDecimal.valueOf(1));
        when(productRepository.save(any())).thenReturn(product);

        ProductDto result = productService.save(productSaveRequestDto);

        assertEquals(result.getName(),productSaveRequestDto.getName());

        verify(vatService).findById(anyLong());
    }

    @Test
    void shouldNotSaveWhenParameterIsNull() {
        assertThrows(NullPointerException.class, () -> productService.save(null));
    }

    @Test
    void shouldUpdate() {

        ProductUpdateRequestDto productUpdateRequestDto = Mockito.mock(ProductUpdateRequestDto.class);
        Vat vat = Mockito.mock(Vat.class);
        Product product = Mockito.mock(Product.class);

        Mockito.doReturn(product).when(productService).findById(anyLong());
        when(vatService.findById(anyLong())).thenReturn(vat);
        when(productUpdateRequestDto.getTaxFreePrice()).thenReturn(BigDecimal.valueOf(1));
        when(product.getTaxFreePrice()).thenReturn(BigDecimal.valueOf(1));
        when(vat.getRate()).thenReturn(BigDecimal.valueOf(1));
        when(productRepository.save(product)).thenReturn(product);

        ProductDto result = productService.update(productUpdateRequestDto);

        assertEquals(result.getId(),productUpdateRequestDto.getId());

        verify(vatService).findById(anyLong());
    }

    @Test
    void shouldNotUpdateWhenCustomerDoesNotExist() {

        ProductUpdateRequestDto productUpdateRequestDto = mock(ProductUpdateRequestDto.class);

        when(productRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> productService.update(productUpdateRequestDto));

        verify(productRepository).findById(anyLong());
    }

    @Test
    void shouldDelete() {

        Product product = mock(Product.class);

        Mockito.doReturn(product).when(productService).findById(anyLong());

        productService.delete(anyLong());

        verify(productService).findById(anyLong());
        verify(productRepository).delete(any());
    }

    @Test
    void shouldNotDeleteWhenIdDoesNotExist() {

        when(productRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> productService.delete(anyLong()));

        verify(productRepository).findById(anyLong());
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

        when(product.getTaxFreePrice()).thenReturn(BigDecimal.valueOf(1));
        when(vatUpdateRequestDto.getRate()).thenReturn(BigDecimal.valueOf(1));
        when(vatService.findById(anyLong())).thenReturn(vat);
        when(productRepository.save(any())).thenReturn(product);

        Product result = productService.updateVatRateAndTaxIncludedPrice(vatUpdateRequestDto,product);
        assertEquals(vatUpdateRequestDto.getId(), result.getId());
    }
}