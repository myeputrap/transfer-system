package com.example.transfersystem.service;

import com.example.transfersystem.payload.GetRegisterAccountTransferResponse;
import com.example.transfersystem.payload.RegisterAccountTransferRequest;

import java.util.List;

public interface RegisterAccountTransferService {
    String addRegisterAccountTransfer(RegisterAccountTransferRequest registerAccountTransferRequest);

    List<GetRegisterAccountTransferResponse> getRegisterAccountTransfer(Long id);
}
