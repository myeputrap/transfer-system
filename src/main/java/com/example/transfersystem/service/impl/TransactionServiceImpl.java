package com.example.transfersystem.service.impl;

import com.example.transfersystem.entity.Account;
import com.example.transfersystem.entity.RegisterAccountTransfer;
import com.example.transfersystem.entity.Transaction;
import com.example.transfersystem.entity.Users;
import com.example.transfersystem.exception.ResourceNotFoundException;
import com.example.transfersystem.payload.TransactionDto;
import com.example.transfersystem.payload.TransactionResponse;
import com.example.transfersystem.payload.TransferRequest;
import com.example.transfersystem.repository.*;
import com.example.transfersystem.service.TransasctionService;
import io.lettuce.core.TransactionResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransasctionService {

    private RegisterAccountTransferRepository registerAccountTransferRepository;

    private BankRepository bankRepository;

    private AccountRepository accountRepository;

    private UserRepository userRepository;

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(RegisterAccountTransferRepository registerAccountTransferRepository, BankRepository bankRepository, AccountRepository accountRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.registerAccountTransferRepository = registerAccountTransferRepository;
        this.bankRepository = bankRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public String TransferBetweenCust(TransferRequest transferRequest) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transferRequest.getAmount());
        transaction.setTotalAmount(transferRequest.getAmount());
        BigDecimal totalAmount;
        RegisterAccountTransfer registerAccountTransfer = registerAccountTransferRepository.findById(transferRequest.getRegisterAccountTransferId())
                .orElseThrow(() -> new ResourceNotFoundException("RegisterAccountTransfer", "id", transferRequest.getRegisterAccountTransferId()));
//
//        Account accountSender = accountRepository.findById(registerAccountTransfer.getSenderAccount().getId())
//                .orElseThrow(() -> new ResourceNotFoundException("AccountSender", "id", registerAccountTransfer.getSenderAccount().getId()));
//
//        Account accountReceiver = accountRepository.findById(registerAccountTransfer.getReceiverAccount().getId())
//                .orElseThrow(() -> new ResourceNotFoundException("AccountReceiver", "id", registerAccountTransfer.getReceiverAccount().getId()));
        if (registerAccountTransfer.getBank() != null && registerAccountTransfer.getBank().getId() != null && registerAccountTransfer.getBank().getId() != 0) {
            totalAmount = transferRequest.getAmount().add(BigDecimal.valueOf(6500));
            transaction.setTotalAmount(totalAmount);
        }
        transaction.setRegisterAccountTransfer(registerAccountTransfer);
        transaction.setDtmCrt(LocalDateTime.now());
        transactionRepository.save(transaction);
        return "Success";
    }

    @Override
    public TransactionDto getAllTransaction(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Transaction> posts = transactionRepository.findAll(pageable);
        List<Transaction> res = posts.getContent();
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (int i = 0; i < res.size(); i++) {
        TransactionResponse transactionResponse = new TransactionResponse();
        Transaction item = res.get(i);


        RegisterAccountTransfer registerAccountTransfer = registerAccountTransferRepository.findById(item.getRegisterAccountTransfer().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("AccountSender", "id", item.getRegisterAccountTransfer().getId()));

        Account accountSender = accountRepository.findById(registerAccountTransfer.getSenderAccount().getId())
                .orElseThrow(() -> new ResourceNotFoundException("AccountSender", "id", registerAccountTransfer.getSenderAccount().getId()));

        Account accountReceiver = accountRepository.findById(registerAccountTransfer.getReceiverAccount().getId())
                .orElseThrow(() -> new ResourceNotFoundException("AccountReceiver", "id", registerAccountTransfer.getReceiverAccount().getId()));

        Users user = userRepository.findById(registerAccountTransfer.getReceiverAccount().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("AccountReceiver", "id", registerAccountTransfer.getReceiverAccount().getId()));
            transactionResponse.setDtmCrt(item.getDtmCrt());
            transactionResponse.setReceiverName(user.getName());
            transactionResponse.setAccountNumber(accountReceiver.getAccountNumber());
            transactionResponse.setAmount(item.getAmount());
            transactionResponse.setAmount(item.getTotalAmount());
            transactionResponses.add(transactionResponse);
        }


        TransactionDto TransactionDto = new TransactionDto();
        TransactionDto.setContent(transactionResponses);
        TransactionDto.setPageNo(posts.getNumber());
        TransactionDto.setPageSize(posts.getSize());
        TransactionDto.setTotalElements(posts.getTotalElements());
        TransactionDto.setTotalPages(posts.getTotalPages());
        TransactionDto.setLast(posts.isLast());
        return TransactionDto;
    }


}
