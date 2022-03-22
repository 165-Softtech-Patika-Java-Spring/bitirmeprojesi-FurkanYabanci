package com.softtech.market.controller;

import com.softtech.market.dto.GeneralResponseDto;
import com.softtech.market.dto.UserDto;
import com.softtech.market.dto.request.UserSaveRequestDto;
import com.softtech.market.service.AuthenticationService;
import com.softtech.market.dto.request.SecurityLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody SecurityLoginRequestDto securityLoginRequestDto){
        String token = authenticationService.login(securityLoginRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserSaveRequestDto userSaveRequestDto){
        UserDto userDto = authenticationService.register(userSaveRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(userDto));
    }
}
