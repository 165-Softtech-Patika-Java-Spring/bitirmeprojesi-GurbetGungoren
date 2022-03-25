package com.softtech.finalproject.security.controller;

import com.softtech.finalproject.dto.user.UserResponse;
import com.softtech.finalproject.dto.user.UserSaveRequestDto;
import com.softtech.finalproject.gen.dto.RestResponse;
import com.softtech.finalproject.security.dto.SecurityLoginRequestDto;
import com.softtech.finalproject.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(tags = "Authentication Controller")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody SecurityLoginRequestDto securityLoginRequestDto){

        String token = authenticationService.login(securityLoginRequestDto);

        return ResponseEntity.ok(RestResponse.of(token));
    }

    @Operation(tags = "Authentication Controller")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserSaveRequestDto userSaveRequestDto){

        UserResponse register = authenticationService.register(userSaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(register));
    }
}
