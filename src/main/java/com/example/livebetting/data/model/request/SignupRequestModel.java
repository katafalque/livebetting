package com.example.livebetting.data.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignupRequestModel {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
}
