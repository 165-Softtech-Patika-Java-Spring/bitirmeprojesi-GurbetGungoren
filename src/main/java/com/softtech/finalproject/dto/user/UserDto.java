package com.softtech.finalproject.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String userName;
}
