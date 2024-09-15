package com.example.livebetting.service.impl;

import com.example.livebetting.data.entity.User;
import com.example.livebetting.data.model.request.LoginRequestModel;
import com.example.livebetting.data.model.request.SignupRequestModel;
import com.example.livebetting.data.repository.UserRepository;
import com.example.livebetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void register(SignupRequestModel signupRequestModel) {
        User user = User.builder()
                .firstName(signupRequestModel.getFirstName())
                .lastName(signupRequestModel.getLastName())
                .userName(signupRequestModel.getUserName())
                .password(passwordEncoder.encode(signupRequestModel.getPassword()))
                .build();

        this.userRepository.save(user);
    }

    @Override
    public User login(LoginRequestModel loginRequestModel) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestModel.getUsername(),
                        loginRequestModel.getPassword()
                )
        );

        return this.userRepository.findByUserName(loginRequestModel.getUsername())
                .orElseThrow();
    }
}
