package com.softtech.finalproject.service;

import com.softtech.finalproject.dto.*;
import com.softtech.finalproject.service.EntityService.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponse createUser(UserSaveRequestDto userSaveRequestDto);
    UserResponse updateUser(UserUpdateRequestDto userUpdateRequestDto);
    void deleteUser(Long id);
}
