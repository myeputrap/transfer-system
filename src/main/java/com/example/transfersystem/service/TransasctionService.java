package com.example.transfersystem.service;

import com.example.transfersystem.payload.TransactionDto;
import com.example.transfersystem.payload.TransferRequest;

public interface TransasctionService {

    String TransferBetweenCust (TransferRequest transferRequest);

    TransactionDto getAllTransaction(int pageNo, int pageSize, String sortBy, String sortDir);
}
