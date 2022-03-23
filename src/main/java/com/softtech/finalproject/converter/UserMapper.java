package com.softtech.finalproject.converter;

import com.softtech.finalproject.dto.UserResponse;
import com.softtech.finalproject.dto.UserSaveRequestDto;
import com.softtech.finalproject.dto.UserUpdateRequestDto;
import com.softtech.finalproject.model.UserEntity;
import org.apache.catalina.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserEntity convertToUser(UserSaveRequestDto userSaveRequestDto);
    UserResponse convertToResponse(UserEntity userEntity);
    UserEntity convertToUser(UserUpdateRequestDto userUpdateRequestDto);

}
