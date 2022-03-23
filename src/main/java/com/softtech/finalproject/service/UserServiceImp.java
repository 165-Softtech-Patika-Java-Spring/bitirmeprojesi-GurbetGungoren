package com.softtech.finalproject.service;

import com.softtech.finalproject.converter.UserMapper;
import com.softtech.finalproject.dto.UserResponse;
import com.softtech.finalproject.dto.UserSaveRequestDto;
import com.softtech.finalproject.dto.UserUpdateRequestDto;
import com.softtech.finalproject.gen.enums.GenErrorMessage;
import com.softtech.finalproject.gen.exceptions.GenBusinessException;
import com.softtech.finalproject.gen.exceptions.ItemNotFoundException;
import com.softtech.finalproject.model.UserEntity;
import com.softtech.finalproject.service.EntityService.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserEntityService userEntityService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponse createUser(UserSaveRequestDto userSaveRequestDto) {
        if (userEntityService.existsByUserName(userSaveRequestDto.getUserName())){
            throw new GenBusinessException(GenErrorMessage.USERNAME_IS_USED);
        }
        UserEntity user = UserMapper.INSTANCE.convertToUser(userSaveRequestDto);
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        return UserMapper.INSTANCE.convertToResponse(userEntityService.save(user));
    }
    @Override
    public UserResponse updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        controlIsUserExist(userUpdateRequestDto.getId());
        UserEntity entity = UserMapper.INSTANCE.convertToUser(userUpdateRequestDto);
        UserEntity user = userEntityService.save(entity);
        return UserMapper.INSTANCE.convertToResponse(user);
    }
    @Override
    public void deleteUser(Long id) {
        UserEntity user = userEntityService.getByIdWİthControl(id);
        userEntityService.delete(user);
    }
    private void controlIsUserExist(Long id) {
        boolean isExist = userEntityService.existsById(id);
        if (!isExist){
            throw new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND);
        }
    }



}
