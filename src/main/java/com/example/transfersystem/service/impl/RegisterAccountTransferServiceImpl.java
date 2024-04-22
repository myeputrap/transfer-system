package com.example.transfersystem.service.impl;

import com.example.transfersystem.entity.Account;
import com.example.transfersystem.entity.Bank;
import com.example.transfersystem.entity.RegisterAccountTransfer;
import com.example.transfersystem.entity.Users;
import com.example.transfersystem.payload.GetRegisterAccountTransferResponse;
import com.example.transfersystem.payload.RegisterAccountTransferRequest;
import com.example.transfersystem.repository.AccountRepository;
import com.example.transfersystem.repository.BankRepository;
import com.example.transfersystem.exception.ResourceNotFoundException;
import com.example.transfersystem.repository.RegisterAccountTransferRepository;
import com.example.transfersystem.repository.UserRepository;
import com.example.transfersystem.service.RegisterAccountTransferService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegisterAccountTransferServiceImpl implements RegisterAccountTransferService {

    private RegisterAccountTransferRepository registerAccountTransferRepository;

    private BankRepository bankRepository;

    private AccountRepository accountRepository;

    private UserRepository userRepository;

    public RegisterAccountTransferServiceImpl(RegisterAccountTransferRepository registerAccountTransferRepository, BankRepository bankRepository, AccountRepository accountRepository, UserRepository userRepository) {
        this.registerAccountTransferRepository = registerAccountTransferRepository;
        this.bankRepository = bankRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String addRegisterAccountTransfer(RegisterAccountTransferRequest registerAccountTransferRequest) {
        RegisterAccountTransfer registerAccountTransfer = new RegisterAccountTransfer();
       if (registerAccountTransferRequest.getBankID() != null || registerAccountTransferRequest.getBankID() != 0){
           Bank bank = bankRepository.findById(registerAccountTransferRequest.getBankID())
                   .orElseThrow(() -> new ResourceNotFoundException("Bank", "id", registerAccountTransferRequest.getBankID()));
           registerAccountTransfer.setBank(bank);
       }
        Account accountSender = accountRepository.findById(registerAccountTransferRequest.getSenderId())
                .orElseThrow(() -> new ResourceNotFoundException("AccountSender", "id", registerAccountTransferRequest.getSenderId()));

        Account accountReceiver = accountRepository.findById(registerAccountTransferRequest.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException("AccountReceiver", "id", registerAccountTransferRequest.getReceiverId()));
        registerAccountTransfer.setDtmCrt(LocalDateTime.now());
        registerAccountTransfer.setSenderAccount(accountSender);
        registerAccountTransfer.setReceiverAccount(accountReceiver);

        registerAccountTransferRepository.save(registerAccountTransfer);
        return "Success";
    }

    @Override
    public List<GetRegisterAccountTransferResponse> getRegisterAccountTransfer(Long id) {
        List<GetRegisterAccountTransferResponse> res = new ArrayList<>();
        List<RegisterAccountTransfer> registerAccountTransferByIDSender = registerAccountTransferRepository.getRegisterAccountTransferByIDSender(id);
        for (int i = 0; i < registerAccountTransferByIDSender.size(); i++) {
            GetRegisterAccountTransferResponse resact = new GetRegisterAccountTransferResponse();
            RegisterAccountTransfer item = registerAccountTransferByIDSender.get(i);
            resact.setBank("Same with user");
            Account accountSender = accountRepository.findById(item.getReceiverAccount().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("AccountReceiver", "id", item.getReceiverAccount().getId()));

            Users user = userRepository.findById(accountSender.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("AccountReceiver", "id", accountSender.getUser().getId()));

            resact.setAccountNumber(accountSender.getAccountNumber());
            resact.setName(user.getName());
            if (item.getBank() != null && item.getBank().getId() != null && item.getBank().getId() != 0) {
                Bank bank = bankRepository.findById(item.getBank().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Bank", "id", item.getBank().getId()));
                resact.setBank(bank.getName());

            }

            res.add(resact);
        }
        return res;
    }
}
