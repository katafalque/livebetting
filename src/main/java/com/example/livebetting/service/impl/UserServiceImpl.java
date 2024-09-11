package com.example.livebetting.service.impl;

import com.example.livebetting.data.entity.User;
import com.example.livebetting.data.repository.UserRepository;
import com.example.livebetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }
}
