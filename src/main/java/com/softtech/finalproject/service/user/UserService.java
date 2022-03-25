package com.softtech.finalproject.service.user;

import com.softtech.finalproject.dto.user.UserResponse;
import com.softtech.finalproject.dto.user.UserSaveRequestDto;
import com.softtech.finalproject.dto.user.UserUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface UserService {
    UserResponse createUser(UserSaveRequestDto userSaveRequestDto);
    UserResponse updateUser(UserUpdateRequestDto userUpdateRequestDto);
    void deleteUser(Long id);
}
