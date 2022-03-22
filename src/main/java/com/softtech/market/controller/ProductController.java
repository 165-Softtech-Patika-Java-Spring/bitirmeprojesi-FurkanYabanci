package com.softtech.market.controller;

import com.softtech.market.dto.GeneralResponseDto;
import com.softtech.market.dto.ProductDto;
import com.softtech.market.dto.request.ProductSaveRequestDto;
import com.softtech.market.dto.request.ProductUpdateRequestDto;
import com.softtech.market.model.ProductType;
import com.softtech.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity findAll(){
        List<ProductDto> productDtoList = productService.findAll();
        return ResponseEntity.ok(GeneralResponseDto.of(productDtoList));
    }

    @GetMapping("/findAll/{productType}")
    public ResponseEntity findAllByProductType(@PathVariable ProductType productType){
        List<ProductDto> productDtoList = productService.findAllByProductType(productType);
        return ResponseEntity.ok(GeneralResponseDto.of(productDtoList));
    }

    @GetMapping("/taxIncludedPrice")
    public ResponseEntity findAllByTaxIncludedPriceBetween(@RequestParam BigDecimal startPrice, @RequestParam BigDecimal endPrice){
        List<ProductDto> productDtoList = productService.findAllByTaxIncludedPriceBetween(startPrice, endPrice);
        return ResponseEntity.ok(GeneralResponseDto.of(productDtoList));
    }

    @GetMapping("/productDetails")
    public ResponseEntity findProductDetails(){
        return ResponseEntity.ok(GeneralResponseDto.of(productService.findProductDetails()));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ProductSaveRequestDto productSaveRequestDto) {
        ProductDto productDto = productService.save(productSaveRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(productDto));
    }

    @PatchMapping
    public ResponseEntity update(@RequestBody ProductUpdateRequestDto productUpdateRequestDto){
        ProductDto productDto = productService.update(productUpdateRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        productService.delete(id);
        return ResponseEntity.ok(GeneralResponseDto.of("Product Deleted!"));
    }
}
