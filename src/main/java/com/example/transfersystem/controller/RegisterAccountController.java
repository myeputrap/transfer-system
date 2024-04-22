package com.example.transfersystem.controller;


import com.example.transfersystem.payload.*;
import com.example.transfersystem.service.RegisterAccountTransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class RegisterAccountController {

    private RegisterAccountTransferService registerAccountTransferService;

    public RegisterAccountController(RegisterAccountTransferService registerAccountTransferService) {
        this.registerAccountTransferService = registerAccountTransferService;
    }

    @PostMapping(value = {"register-account-tr"})
    public ResponseEntity<String> AddRegisterAccountTransfer(@RequestBody RegisterAccountTransferRequest loginDto){
        String respon = registerAccountTransferService.addRegisterAccountTransfer(loginDto);
        return new ResponseEntity<>(respon, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("register-account-tr/{id}")
    public List<GetRegisterAccountTransferResponse> getRegisterAccountTransfer(@PathVariable(name = "id") long id){
        return registerAccountTransferService.getRegisterAccountTransfer(id);
    }
}
