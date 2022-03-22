package com.softtech.market.service;

import com.softtech.market.converter.VatMapper;
import com.softtech.market.dto.VatDto;
import com.softtech.market.dto.request.VatSaveRequestDto;
import com.softtech.market.dto.request.VatUpdateRequestDto;
import com.softtech.market.enums.VatErrorMessage;
import com.softtech.market.exception.ItemNotFoundException;
import com.softtech.market.model.Product;
import com.softtech.market.model.Vat;
import com.softtech.market.repository.VatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VatService {

    private final VatRepository vatRepository;
    private ProductService productService;
    private final BaseService<Vat> baseService;

    @Autowired
    public void setProductService(@Lazy ProductService productService) {
        this.productService = productService;
    }

    public VatDto save(VatSaveRequestDto vatSaveRequestDto){
        Vat vat = VatMapper.INSTANCE.convertToVat(vatSaveRequestDto);
        baseService.addBaseAdditionalFields(vat);
        vat = vatRepository.save(vat);
        VatDto vatDto = VatMapper.INSTANCE.convertToVatDto(vat);
        return vatDto;
    }

    @Transactional
    public VatDto update(VatUpdateRequestDto vatUpdateRequestDto){
        Vat vat = findById(vatUpdateRequestDto.getId());
        vat.setRate(vatUpdateRequestDto.getRate());
        baseService.addBaseAdditionalFields(vat);
        List<Product> products = productService.findAllByVatId(vatUpdateRequestDto.getId());
        for (Product product:products)
        {
            productService.updateVatRateAndTaxIncludedPrice(vatUpdateRequestDto,product);
        }
        vat = vatRepository.save(vat);
        VatDto vatDto = VatMapper.INSTANCE.convertToVatDto(vat);
        return vatDto;
    }

    public void delete(long id){
        Vat vat = findById(id);
        vatRepository.delete(vat);
    }

    protected Vat findById(long id){
        Vat vat = vatRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(VatErrorMessage.VAT_NOT_FOUND));
        return vat;
    }
}
