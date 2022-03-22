package com.softtech.market.controller;

import com.softtech.market.dto.GeneralResponseDto;
import com.softtech.market.dto.VatDto;
import com.softtech.market.dto.request.VatSaveRequestDto;
import com.softtech.market.dto.request.VatUpdateRequestDto;
import com.softtech.market.service.VatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vats")
public class VatController {

    private final VatService vatService;

    @PostMapping
    public ResponseEntity save(@RequestBody VatSaveRequestDto productTypeSaveRequestDto) {
        VatDto vatDto = vatService.save(productTypeSaveRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(vatDto));
    }

    @PatchMapping
    public ResponseEntity update(@RequestBody VatUpdateRequestDto vatUpdateRequestDto){
        VatDto vatDto = vatService.update(vatUpdateRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(vatDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        vatService.delete(id);
        return ResponseEntity.ok(GeneralResponseDto.of("Vat Deleted"));
    }
}
