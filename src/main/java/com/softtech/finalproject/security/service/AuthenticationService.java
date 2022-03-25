package com.softtech.finalproject.security.service;

import com.softtech.finalproject.dto.UserResponse;
import com.softtech.finalproject.dto.UserSaveRequestDto;
import com.softtech.finalproject.model.UserEntity;
import com.softtech.finalproject.security.dto.SecurityLoginRequestDto;
import com.softtech.finalproject.security.enums.EnumJwtConstant;
import com.softtech.finalproject.security.sec.JwtTokenGenerator;
import com.softtech.finalproject.security.sec.JwtUserDetails;
import com.softtech.finalproject.service.EntityService.UserEntityService;
import com.softtech.finalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final UserEntityService userEntityService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;

    public UserResponse register(UserSaveRequestDto userSaveRequestDto) {
        UserResponse user = userService.createUser(userSaveRequestDto);
        return user;
    }

    public String login(SecurityLoginRequestDto securityLoginRequestDto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityLoginRequestDto.getUserName(), securityLoginRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenGenerator.generateJwtToken(authentication);

        String bearer = EnumJwtConstant.BEARER.getConstant();

        return bearer + token;
    }

    public UserEntity getCurrentUser() {
        JwtUserDetails jwtUserDetails = getCurrentJwtUserDetails();
        UserEntity userEntity = null;
        if (jwtUserDetails != null){
            userEntity = userEntityService.getByIdWithControl(jwtUserDetails.getId());
        }
        return userEntity  ;
    }

    public Long getCurrentCustomerId(){

        JwtUserDetails jwtUserDetails = getCurrentJwtUserDetails();

        Long jwtUserDetailsId = null;
        if (jwtUserDetails != null){
            jwtUserDetailsId = jwtUserDetails.getId();
        }

        return jwtUserDetailsId;
    }

    private JwtUserDetails getCurrentJwtUserDetails() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtUserDetails jwtUserDetails = null;
        if (authentication != null && authentication.getPrincipal() instanceof JwtUserDetails){
            jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        }
        return jwtUserDetails;
    }
}

