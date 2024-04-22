package com.example.transfersystem.payload;

import com.example.transfersystem.entity.RegisterAccountTransfer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private Long registerAccountTransferId;


    private BigDecimal amount;
}
