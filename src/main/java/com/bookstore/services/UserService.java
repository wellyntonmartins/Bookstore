package com.bookstore.services;

import com.bookstore.exceptions.DataFormatWrongException;
import com.bookstore.models.UserModel;
import com.bookstore.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel userExists(String email) {
        if (!StringUtils.hasText(email)) {
            throw new DataFormatWrongException("The provided email can't be empty or null.");
        }

        UserModel userFound = userRepository.findByEmail(email);

        if (Objects.isNull(userFound)) {
            throw new DataFormatWrongException("User or password is incorrect.");
        }

        return userFound;
    }
}
