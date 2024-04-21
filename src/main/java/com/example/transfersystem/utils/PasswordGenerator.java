package com.example.transfersystem.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        PasswordEncoder psw = new BCryptPasswordEncoder();
        System.out.println(psw.encode("password"));
    }
}
