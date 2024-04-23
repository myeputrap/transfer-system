package com.example.transfersystem.repository;

import com.example.transfersystem.entity.Bank;
import com.example.transfersystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long>  {
    long count();
}
