package com.example.transfersystem.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "register_account_transfer_id")
    private RegisterAccountTransfer registerAccountTransfer;

    @DecimalMin(value = "10000", message = "Amount must be at least 10000")
    private BigDecimal amount;
    @DecimalMin(value = "10000", message = "Amount must be at least 10000")
    private BigDecimal totalAmount;
    private LocalDateTime dtmCrt;

    private LocalDateTime dtmUpd;
}
