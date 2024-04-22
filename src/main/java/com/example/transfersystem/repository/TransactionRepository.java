package com.example.transfersystem.repository;

import com.example.transfersystem.entity.Transaction;
import com.example.transfersystem.payload.TransactionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t.dtmCrt, u.name, a.accountNumber, t.amount, t.totalAmount, a.balance " +
            "FROM Transaction t " +
            "JOIN t.registerAccountTransfer rat " +
            "JOIN rat.receiverAccount a " +
            "JOIN a.user u")
    List<TransactionResponse> customQuery();
}
