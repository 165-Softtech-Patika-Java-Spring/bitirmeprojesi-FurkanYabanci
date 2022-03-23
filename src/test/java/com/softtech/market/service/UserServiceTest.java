package com.softtech.market.service;

import com.softtech.market.dto.UserDto;
import com.softtech.market.dto.request.UserSaveRequestDto;
import com.softtech.market.dto.request.UserUpdateRequestDto;
import com.softtech.market.exception.ItemNotFoundException;
import com.softtech.market.model.User;
import com.softtech.market.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    @InjectMocks
    private UserService userService;

    @Test
    void shouldSave() {
        UserSaveRequestDto userSaveRequestDto = Mockito.mock(UserSaveRequestDto.class);
        User user = Mockito.mock(User.class);

        Mockito.when(userSaveRequestDto.getPassword()).thenReturn("12345");
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("1234567");
        Mockito.when(userRepository.save(any())).thenReturn(user);

        UserDto result = userService.save(userSaveRequestDto);

        assertEquals(result.getUsername(), userSaveRequestDto.getUsername());
    }

    @Test
    void shouldNotSaveWhenParameterIsNull() {
        assertThrows(NullPointerException.class, () -> userService.save(null));
    }

    @Test
    void shouldUpdate() {
        UserUpdateRequestDto userUpdateRequestDto = Mockito.mock(UserUpdateRequestDto.class);
        User user = Mockito.mock(User.class);

        Mockito.doReturn(user).when(userService).findById(anyLong());
        Mockito.when(userRepository.save(any())).thenReturn(user);

        UserDto result = userService.update(userUpdateRequestDto);

        assertEquals(result.getId(), userUpdateRequestDto.getId());
    }

    @Test
    void shouldNotUpdateWhenUserDoesNotExist() {

        UserUpdateRequestDto userUpdateRequestDto = mock(UserUpdateRequestDto.class);

        when(userRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> userService.update(userUpdateRequestDto));

        verify(userRepository).findById(anyLong());
    }

    @Test
    void shouldDelete() {
        User user = Mockito.mock(User.class);

        Mockito.doReturn(user).when(userService).findById(anyLong());

        userService.delete(anyLong());

        Mockito.verify(userService).findById(anyLong());
        Mockito.verify(userRepository).delete(any());
    }

    @Test
    void shouldNotDeleteWhenIdDoesNotExist() {

        when(userRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> userService.delete(anyLong()));

        verify(userRepository).findById(anyLong());
    }

    @Test
    void shouldFindByUsername() {

        User user = Mockito.mock(User.class);

        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);

        User result = userService.findByUsername(anyString());

        assertEquals(result.getUsername(), user.getUsername());
    }

    @Test
    void shouldNotFindByUsernameWhenUsernameDoesNotExist() {
        when(userRepository.findByUsername(anyString())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> userService.findByUsername(anyString()));
        verify(userRepository).findByUsername(anyString());
    }

    @Test
    void shouldFindById() {
        User user = Mockito.mock(User.class);

        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        User result = userService.findById(anyLong());

        assertEquals(result.getId(), user.getId());
    }

    @Test
    void shouldNotFindByIdWhenIdDoesNotExist(){
        when(userRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> userService.findById(anyLong()));
        verify(userRepository).findById(anyLong());
    }

}