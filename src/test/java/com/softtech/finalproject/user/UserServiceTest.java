package com.softtech.finalproject.user;

import com.softtech.finalproject.dto.user.UserResponse;
import com.softtech.finalproject.dto.user.UserSaveRequestDto;
import com.softtech.finalproject.dto.user.UserUpdateRequestDto;
import com.softtech.finalproject.gen.exceptions.GenBusinessException;
import com.softtech.finalproject.gen.exceptions.ItemNotFoundException;
import com.softtech.finalproject.model.UserEntity;
import com.softtech.finalproject.service.EntityService.UserEntityService;
import com.softtech.finalproject.service.user.UserService;
import com.softtech.finalproject.service.user.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    UserService userService;
    @Mock
    UserEntityService userEntityService;
    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup(){
        userService=new UserServiceImp(
                userEntityService,passwordEncoder
        );
    }
    @Test
    void should_create_user(){
        //given
        UserSaveRequestDto userSaveRequestDto=mock(UserSaveRequestDto.class);
        when(userSaveRequestDto.getUserName()).thenReturn("test_user");
        //mock
        UserEntity userEntity=mock(UserEntity.class);
        when(userEntity.getId()).thenReturn(1L);
        when(userEntity.getUserName()).thenReturn("test_user");
        when(userEntityService.existsByUserName(any())).thenReturn(Boolean.FALSE);
        when(passwordEncoder.encode(any())).thenReturn("encoded_password");
        when(userEntityService.save(any())).thenReturn(userEntity);
        //when
        UserResponse createdUser = userService.createUser(userSaveRequestDto);
        //then
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getUserName()).isEqualTo("test_user");
    }
    @Test
    void should_not_create_user_if_user_name_exist(){
        //given
        UserSaveRequestDto userSaveRequestDto=mock(UserSaveRequestDto.class);
        when(userSaveRequestDto.getUserName()).thenReturn("test_user");
        when(userEntityService.existsByUserName(any())).thenReturn(Boolean.TRUE);
        Throwable throwable=catchThrowable(()-> userService.createUser(userSaveRequestDto));
        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(GenBusinessException.class);
    }
    @Test
    void should_update_user(){
        //mock
        UserUpdateRequestDto userUpdateRequestDto=mock(UserUpdateRequestDto.class);
        when(userUpdateRequestDto.getUserName()).thenReturn("test_user");
        when(userUpdateRequestDto.getId()).thenReturn(1L);

        UserEntity userEntity=mock(UserEntity.class);

        when(userEntityService.existsById(any())).thenReturn(Boolean.TRUE);
        when(userEntityService.save(any())).thenReturn(userEntity);

        //when
        UserResponse response = userService.updateUser(userUpdateRequestDto);
        assertThat(response).isNotNull();
    }
    @Test
    void should_not_update_when_id_not_exist(){
        UserUpdateRequestDto userUpdateRequestDto=mock(UserUpdateRequestDto.class);
        when(userEntityService.existsById(any())).thenReturn(Boolean.FALSE);
        ItemNotFoundException itemNotFoundException = assertThrows(ItemNotFoundException.class, () -> userService.updateUser(userUpdateRequestDto));
        assertThat(itemNotFoundException).isNotNull();
        verifyNoMoreInteractions(userEntityService);
    }
    @Test
    void should_not_delete_when_user_not_exist(){
        Long id=1L;
        when(userEntityService.getByIdWithControl(id)).thenThrow(ItemNotFoundException.class);
        ItemNotFoundException itemNotFoundException = assertThrows(ItemNotFoundException.class, () -> userService.deleteUser(id));
        assertThat(itemNotFoundException).isNotNull();
        verifyNoMoreInteractions(userEntityService);
    }
}
