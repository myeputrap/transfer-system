package com.example.transfersystem.service.impl;

import com.example.transfersystem.entity.Bank;
import com.example.transfersystem.exception.ErrorAPIException;
import com.example.transfersystem.payload.BankDto;
import com.example.transfersystem.repository.BankRepository;
import com.example.transfersystem.service.BankService;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {
    private static final Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);

    private ModelMapper mapper;
    private BankRepository bankRepository;

    private RedisTemplate redisTemplate;

    private final String key = "BANK";

    public BankServiceImpl(ModelMapper mapper, BankRepository bankRepository, RedisTemplate redisTemplate) {
        this.mapper = mapper;
        this.bankRepository = bankRepository;
        this.redisTemplate = redisTemplate;
    }

    private BankDto mapBankToDTO(Bank Bank) {
        BankDto BankDto = mapper.map(Bank, BankDto.class);
        return BankDto;
    }

    @Override
    public List<BankDto> getAllBank() {
        try {
            Boolean hasKey = redisTemplate.hasKey(key);

            if (hasKey != null && hasKey) {
                List<BankDto> bank = redisTemplate.opsForHash().values(key);
                return bank;
            } else {
                List<Bank> bank = bankRepository.findAll();
                for (int i = 0; i < bank.size(); i++) {
                    Bank bankLoop = bank.get(i);
                    redisTemplate.opsForHash().put(key, bankLoop.getId().toString(), bankLoop);
                }
                return bank.stream().map(balikan -> mapBankToDTO(balikan)).collect(Collectors.toList());
            }
        } catch (Exception e) {
            logger.error("Error occurred", e);
            throw new ErrorAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while retrieving banks");
        }
}}




