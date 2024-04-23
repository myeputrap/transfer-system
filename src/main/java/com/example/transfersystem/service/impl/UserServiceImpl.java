package com.example.transfersystem.service.impl;

import com.example.transfersystem.entity.Users;
import com.example.transfersystem.exception.ErrorAPIException;
import com.example.transfersystem.payload.GetProfileResponse;
import com.example.transfersystem.repository.UserRepository;
import com.example.transfersystem.service.UserService;
import com.example.transfersystem.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private SecurityUtils securityUtils;
    private UserRepository userRepository;


    public UserServiceImpl(SecurityUtils securityUtils, UserRepository userRepository) {
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
    }

    @Override
    public GetProfileResponse getProfile() {
        try {
            GetProfileResponse getProfileResponse = new GetProfileResponse();
            String email = securityUtils.getJwtToken();
            Optional<Users> user = userRepository.findByEmail(email);
            getProfileResponse.setEmail(user.get().getEmail());
            getProfileResponse.setName(user.get().getName());
            getProfileResponse.setUsername(user.get().getUsername());
            return getProfileResponse;

        } catch (Exception e) {
            logger.error("Error occurred", e);
            throw new ErrorAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while retrieving profiles");
        }
    }
}
