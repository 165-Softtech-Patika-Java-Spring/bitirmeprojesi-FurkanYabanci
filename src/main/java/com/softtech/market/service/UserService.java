package com.softtech.market.service;

import com.softtech.market.converter.UserMapper;
import com.softtech.market.dto.UserDto;
import com.softtech.market.dto.request.UserSaveRequestDto;
import com.softtech.market.dto.request.UserUpdateRequestDto;
import com.softtech.market.enums.UserErrorMessage;
import com.softtech.market.exception.ItemNotFoundException;
import com.softtech.market.model.BaseAdditionalFields;
import com.softtech.market.model.Product;
import com.softtech.market.model.User;
import com.softtech.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BaseService<User> baseService;

    public UserDto save(UserSaveRequestDto userSaveRequestDto){
        checkUser(userSaveRequestDto);
        User user = UserMapper.INSTANCE.convertToUser(userSaveRequestDto);
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        baseService.addBaseAdditionalFields(user);
        user = userRepository.save(user);
        UserDto userDto = UserMapper.INSTANCE.convertToUserDto(user);
        return userDto;
    }

    public UserDto update(UserUpdateRequestDto userUpdateRequestDto){
        User user = findById(userUpdateRequestDto.getId());
        user.setUsername(userUpdateRequestDto.getUsername());
        user.setPassword(userUpdateRequestDto.getPassword());
        user.setFirstName(userUpdateRequestDto.getFirstName());
        user.setLastName(userUpdateRequestDto.getLastName());
        baseService.addBaseAdditionalFields(user);
        user = userRepository.save(user);
        UserDto userDto = UserMapper.INSTANCE.convertToUserDto(user);
        return userDto;
    }

    public void delete(long id){
        User user = findById(id);
        userRepository.delete(user);
    }

    public User findByUsername(String username){
        User user = userRepository.findByUsername(username);
        return user;
    }

    public User findById(long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(UserErrorMessage.USER_NOT_FOUND));
        return user;
    }

    private void checkUser(UserSaveRequestDto userSaveRequestDto){
        List<User> userList = userRepository.findAll();
        for (User u:userList) {
            if (u.getUsername().equals(userSaveRequestDto.getUsername())){
                throw new ItemNotFoundException(UserErrorMessage.USERNAME_ALREADY_EXİSTS);
            }
        }
    }
}
