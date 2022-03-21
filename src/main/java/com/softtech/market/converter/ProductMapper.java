package com.softtech.market.converter;

import com.softtech.market.dto.ProductDto;
import com.softtech.market.dto.request.ProductSaveRequestDto;
import com.softtech.market.model.Product;
import jdk.dynalink.linker.LinkerServices;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product convertToProduct(ProductSaveRequestDto productSaveRequestDto);
    ProductDto convertToProductDto(Product product);
    List<ProductDto> convertToProductDtoList(List<Product> products);
}
