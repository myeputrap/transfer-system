package com.example.transfersystem.payload;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private LocalDateTime dtmCrt;
    private String accountNumber;

    private BigDecimal balance;
    private String ReceiverName;
    private BigDecimal amount;
    private BigDecimal totalAmount;
}
