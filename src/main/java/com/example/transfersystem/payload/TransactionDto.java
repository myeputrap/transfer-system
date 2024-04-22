package com.example.transfersystem.payload;


import java.util.List;

import com.example.transfersystem.entity.Transaction;
import com.example.transfersystem.service.impl.TransactionServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private List<TransactionResponse> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

}
