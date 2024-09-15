package com.example.livebetting.controller;

import com.example.livebetting.data.entity.User;
import com.example.livebetting.data.model.request.LoginRequestModel;
import com.example.livebetting.data.model.request.SignupRequestModel;
import com.example.livebetting.data.model.response.LoginResponseModel;
import com.example.livebetting.service.JwtService;
import com.example.livebetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestModel signupRequestModel) {
        userService.register(signupRequestModel);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseModel> login(@RequestBody LoginRequestModel loginRequestModel) {
        User user = userService.login(loginRequestModel);
        String token = jwtService.generateToken(user);
        LoginResponseModel loginResponseModel = LoginResponseModel.builder().token("Bearer " + token).build();
        return new ResponseEntity<>(loginResponseModel, HttpStatus.OK);
    }
}
