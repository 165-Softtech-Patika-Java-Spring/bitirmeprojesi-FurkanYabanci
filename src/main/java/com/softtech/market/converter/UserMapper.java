package com.softtech.market.converter;

import com.softtech.market.dto.UserDto;
import com.softtech.market.dto.request.UserSaveRequestDto;
import com.softtech.market.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToUser(UserSaveRequestDto userSaveRequestDto);
    UserDto convertToUserDto(User user);
}
