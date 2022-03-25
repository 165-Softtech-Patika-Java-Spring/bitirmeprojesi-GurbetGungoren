package com.softtech.finalproject.converter;

import com.softtech.finalproject.dto.user.UserResponse;
import com.softtech.finalproject.dto.user.UserSaveRequestDto;
import com.softtech.finalproject.dto.user.UserUpdateRequestDto;
import com.softtech.finalproject.model.UserEntity;
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
