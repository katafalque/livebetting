package com.example.livebetting.service;

import com.example.livebetting.data.model.request.SignupRequestModel;

public interface UserService {
    void register(SignupRequestModel signupRequestModel);
}
