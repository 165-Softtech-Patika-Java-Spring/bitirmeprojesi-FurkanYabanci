package com.softtech.market.service;

import com.softtech.market.dto.VatDto;
import com.softtech.market.dto.request.VatSaveRequestDto;
import com.softtech.market.dto.request.VatUpdateRequestDto;
import com.softtech.market.exception.ItemNotFoundException;
import com.softtech.market.model.Product;
import com.softtech.market.model.Vat;
import com.softtech.market.repository.VatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VatServiceTest {

    @Mock
    private VatRepository vatRepository;

    @Mock
    private ProductService productService;

    @Mock
    private BaseService<Vat> baseService;

    @Spy
    @InjectMocks
    private VatService vatService;

    @Test
    void shouldSave() {
        VatSaveRequestDto vatSaveRequestDto = Mockito.mock(VatSaveRequestDto.class);
        Vat vat = Mockito.mock(Vat.class);

        Mockito.when(vatRepository.save(any())).thenReturn(vat);

        VatDto result = vatService.save(vatSaveRequestDto);

        assertEquals(result.getProductType(), vatSaveRequestDto.getProductType());
    }

    @Test
    void shouldUpdate() {
/*
        VatUpdateRequestDto vatUpdateRequestDto = Mockito.mock(VatUpdateRequestDto.class);
        Vat vat = Mockito.mock(Vat.class);
        Product product = Mockito.mock(Product.class);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        doReturn(vat).when(vatService).findById(anyLong());
        when(productService.findAllByVatId(anyLong())).thenReturn(productList);
        // when(productService.updateVatRateAndTaxIncludedPrice(vatUpdateRequestDto,product)).thenReturn(product);

        VatDto result = vatService.update(vatUpdateRequestDto);
        assertEquals(result.getProductType(),vatUpdateRequestDto.getProductType());*/
    }

    @Test
    void shouldDelete() {
        Vat vat = mock(Vat.class);

        Mockito.doReturn(vat).when(vatService).findById(anyLong());

        vatService.delete(anyLong());

        verify(vatService).findById(anyLong());
        verify(vatRepository).delete(any());

    }

    @Test
    void shouldNotDeleteWhenIdDoesNotExist() {

        when(vatRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> vatService.delete(anyLong()));

        verify(vatRepository).findById(anyLong());
    }

    @Test
    void shouldFindById() {
        Vat vat = mock(Vat.class);

        when(vatRepository.findById(anyLong())).thenReturn(Optional.ofNullable(vat));

        Vat result = vatService.findById(anyLong());
        assertEquals(vat.getId(),result.getId());
    }

    @Test
    void shouldNotFindByIdWhenIdDoesNotExist(){
        when(vatRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> vatService.findById(anyLong()));
        verify(vatRepository).findById(anyLong());
    }
}