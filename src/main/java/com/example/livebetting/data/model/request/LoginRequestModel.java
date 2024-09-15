package com.example.livebetting.data.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequestModel {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
}
