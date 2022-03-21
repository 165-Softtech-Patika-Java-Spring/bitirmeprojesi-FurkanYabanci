package com.softtech.market.converter;

import com.softtech.market.dto.VatDto;
import com.softtech.market.dto.request.VatSaveRequestDto;
import com.softtech.market.model.Vat;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VatMapper {

    VatMapper INSTANCE = Mappers.getMapper(VatMapper.class);

    Vat convertToVat(VatSaveRequestDto vatSaveRequestDto);
    VatDto convertToVatDto(Vat vat);
}
