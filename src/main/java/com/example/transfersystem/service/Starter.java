package com.example.transfersystem.service;

import com.example.transfersystem.entity.Account;
import com.example.transfersystem.entity.Bank;
import com.example.transfersystem.entity.Role;
import com.example.transfersystem.entity.Users;
import com.example.transfersystem.repository.AccountRepository;
import com.example.transfersystem.repository.BankRepository;
import com.example.transfersystem.repository.RoleRepository;
import com.example.transfersystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Starter implements CommandLineRunner {

    private BankRepository bankRepository;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    public Starter(BankRepository bankRepository, AccountRepository accountRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.bankRepository = bankRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            initTest();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private void initTest() {
    long count = bankRepository.count();
    if(count==0){
    List<String> bankNames = Arrays.asList(
            "BANK 1",
            "BANK 2",
            "BANK 3"
    );
        List<Bank> bankList = bankNames.stream()
                .map(name -> {
                    Bank bank = new Bank();
                    bank.setName(name);
                    bank.setDtmCrt(LocalDateTime.now());
                    return bank;
                })
                .collect(Collectors.toList());

        bankRepository.saveAll(bankList);
    }

        long userCount = userRepository.count();
        long roleCount = roleRepository.count();
        long accountCount = accountRepository.count();
        if (userCount == 0 && roleCount == 0 && accountCount == 0) {
            List<Users> userList = Arrays.asList(
                    new Users(1L,"Jack", "jack1", "$2a$10$qmUzHNKOESlGjojC1dWkEOMSmZFQhOvUnQOFMrTA3uFMyz9PFAoZW", "jack@email.com", LocalDateTime.now(), null),
                    new Users(2L,"Doni", "doni2", "$2a$10$qmUzHNKOESlGjojC1dWkEOMSmZFQhOvUnQOFMrTA3uFMyz9PFAoZW", "doni@email.com", LocalDateTime.now(), null),
                    new Users(3L,"Jacob", "jacob3", "$2a$10$qmUzHNKOESlGjojC1dWkEOMSmZFQhOvUnQOFMrTA3uFMyz9PFAoZW", "jacob@email.com", LocalDateTime.now(), null)
            );
            List<Role> roleList = Arrays.asList(
                    new Role(1L, "ROLE_USER")
            );
            userList.forEach(user -> user.setRoles(new HashSet<>(roleList)));

            // Insert accounts
            List<Account> accountList = Arrays.asList(
                    new Account(1L, userList.get(0), "7934347734", new BigDecimal("100000000.00"), LocalDateTime.now(), null),
                    new Account(2L, userList.get(1), "7934347799", new BigDecimal("200000000.00"), LocalDateTime.now(), null),
                    new Account(3L, userList.get(2), "5534347711", new BigDecimal("300000000.00"), LocalDateTime.now(), null)
            );

            userRepository.saveAll(userList);
            accountRepository.saveAll(accountList);
        }
    }
}
