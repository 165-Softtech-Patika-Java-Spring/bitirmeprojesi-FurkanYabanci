package com.softtech.market.controller;

import com.softtech.market.dto.GeneralResponseDto;
import com.softtech.market.dto.UserDto;
import com.softtech.market.dto.request.UserSaveRequestDto;
import com.softtech.market.dto.request.UserUpdateRequestDto;
import com.softtech.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity save(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        UserDto userDto = userService.save(userSaveRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(userDto));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody UserUpdateRequestDto userUpdateRequestDto){
        UserDto userDto = userService.update(userUpdateRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        userService.delete(id);
        return ResponseEntity.ok(GeneralResponseDto.of("User Deleted!"));
    }
}
