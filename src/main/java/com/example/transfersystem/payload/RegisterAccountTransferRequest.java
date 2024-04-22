package com.example.transfersystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAccountTransferRequest {
    private Long senderId;
    private Long receiverId;
    private Long bankID;
}
