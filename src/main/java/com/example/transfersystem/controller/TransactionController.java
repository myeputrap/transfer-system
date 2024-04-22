package com.example.transfersystem.controller;

import com.example.transfersystem.payload.TransactionDto;
import com.example.transfersystem.payload.TransferRequest;
import com.example.transfersystem.service.TransasctionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class TransactionController {

    private TransasctionService transactionService;

    public TransactionController(TransasctionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = {"transaction"})
    public ResponseEntity<String> Transaction(@RequestBody TransferRequest transferRequest){
        String respon = transactionService.TransferBetweenCust(transferRequest);
        return new ResponseEntity<>(respon, HttpStatus.OK);
    }


    @GetMapping("transaction")
    public TransactionDto getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        return transactionService.getAllTransaction(pageNo, pageSize, sortBy, sortDir);
    }
}
