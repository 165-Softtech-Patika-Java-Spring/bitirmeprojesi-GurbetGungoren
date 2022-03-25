package com.softtech.finalproject.dto.user;

import com.softtech.finalproject.gen.exceptions.ItemNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {
    private Long id;
    private String name;
    private String surname;
    private String userName;
    private String password;
}

