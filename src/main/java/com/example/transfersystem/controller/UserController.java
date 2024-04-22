package com.example.transfersystem.controller;

import com.example.transfersystem.payload.BankDto;
import com.example.transfersystem.payload.GetProfileResponse;
import com.example.transfersystem.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("user/profile")
    public GetProfileResponse getProfileResponse(){
        return userService.getProfile();
    }

}
