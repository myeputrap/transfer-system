package com.example.transfersystem.repository;

import com.example.transfersystem.entity.RegisterAccountTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegisterAccountTransferRepository extends JpaRepository<RegisterAccountTransfer, Long> {
    @Query("SELECT u from RegisterAccountTransfer u WHERE u.senderAccount.id= :senderID")
    List<RegisterAccountTransfer> getRegisterAccountTransferByIDSender(@Param("senderID")  long senderID);
}
