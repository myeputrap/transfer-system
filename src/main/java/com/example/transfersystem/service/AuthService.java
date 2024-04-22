package com.example.transfersystem.service;

import com.example.transfersystem.payload.ChangePassword;
import com.example.transfersystem.payload.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String changePassword(ChangePassword changePassword);
}
