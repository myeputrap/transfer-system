package com.example.transfersystem.controller;

import com.example.transfersystem.payload.BankDto;
import com.example.transfersystem.service.BankService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/v1/")
public class BankController {

    private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("bank")
    public List<BankDto> getAllBank(){
        return bankService.getAllBank();
    }

}
