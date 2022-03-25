package com.softtech.finalproject.controller;

import com.softtech.finalproject.dto.user.UserSaveRequestDto;
import com.softtech.finalproject.dto.user.UserUpdateRequestDto;
import com.softtech.finalproject.gen.dto.RestResponse;
import com.softtech.finalproject.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    @Operation(tags = "User Controller")
    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody UserSaveRequestDto userSaveRequestDto){
        return ResponseEntity.ok(RestResponse.of(userService.createUser(userSaveRequestDto)));
    }
    @Operation(tags = "User Controller")
    @PutMapping ("/users")
    public ResponseEntity updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto){
        return ResponseEntity.ok(RestResponse.of(userService.updateUser(userUpdateRequestDto)));
    }
    @Operation(tags = "User Controller")
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
