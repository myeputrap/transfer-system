package com.example.transfersystem.controller;

import com.example.transfersystem.payload.ChangePassword;
import com.example.transfersystem.payload.JWTAuthResponse;
import com.example.transfersystem.payload.LoginDto;
import com.example.transfersystem.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse= new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PutMapping(value = "/user/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword req){

        String response = authService.changePassword(req);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
