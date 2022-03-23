package com.softtech.finalproject.controller;

import com.softtech.finalproject.dto.UserSaveRequestDto;
import com.softtech.finalproject.dto.UserUpdateRequestDto;
import com.softtech.finalproject.gen.dto.RestResponse;
import com.softtech.finalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody UserSaveRequestDto userSaveRequestDto){
        return ResponseEntity.ok(RestResponse.of(userService.createUser(userSaveRequestDto)));
    }
    @PutMapping ("/users")
    public ResponseEntity updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto){
        return ResponseEntity.ok(RestResponse.of(userService.updateUser(userUpdateRequestDto)));
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
