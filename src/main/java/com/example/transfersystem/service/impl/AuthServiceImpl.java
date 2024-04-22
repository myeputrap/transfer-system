package com.example.transfersystem.service.impl;

import com.example.transfersystem.entity.Users;
import com.example.transfersystem.exception.ErrorAPIException;
import com.example.transfersystem.payload.ChangePassword;
import com.example.transfersystem.payload.LoginDto;
import com.example.transfersystem.repository.UserRepository;
import com.example.transfersystem.security.JwtTokenProvider;
import com.example.transfersystem.service.AuthService;
import com.example.transfersystem.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    private UserRepository userRepository;

    private SecurityUtils securityUtils;



    public AuthServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, SecurityUtils securityUtils) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public String login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsernameOrEmail(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(authentication);
            return token;
        } catch (Exception e) {
            logger.error("An error occurred during login", e);
            throw new ErrorAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during login");
        }
    }

    @Override
    public String changePassword(ChangePassword changePassword) {
        try {
        String email = securityUtils.getJwtToken();
        String newPas = passwordEncoder.encode(changePassword.getNewPassword());
        userRepository.changePassword(email, newPas);
        return "Success";
        } catch (Exception e) {
           logger.error("An error occurred changePassword", e);
            throw new ErrorAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }
}