package com.example.transfersystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetRegisterAccountTransferResponse {
    private String AccountNumber;
    private String Name;
    private String Bank;
}
