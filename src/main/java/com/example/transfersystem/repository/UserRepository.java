package com.example.transfersystem.repository;

import com.example.transfersystem.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByUsernameOrEmail(String username, String email);

    Optional<Users> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("Update Users u SET u.password = :newPassword WHERE u.email = :email")
    void changePassword(@Param("email") String email, @Param("newPassword") String newPassword);
}
