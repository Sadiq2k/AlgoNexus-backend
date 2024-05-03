package com.authService.Services.Impl;

import com.authService.Entities.User;
import com.authService.Repository.UserRepository;
import com.authService.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user;
    }

    @Override
    public void saveUser(User user) {
     userRepository.save(user);
    }

    @Override
    public User findUser(UUID id) {
       return userRepository.findById(id);
    }
}
