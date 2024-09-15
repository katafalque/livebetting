package com.example.livebetting.service;

import com.example.livebetting.data.entity.User;
import com.example.livebetting.data.model.request.LoginRequestModel;
import com.example.livebetting.data.model.request.SignupRequestModel;

public interface UserService {
    void register(SignupRequestModel signupRequestModel);

    User login(LoginRequestModel loginRequestModel);
}
