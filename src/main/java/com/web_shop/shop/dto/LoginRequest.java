package com.web_shop.shop.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
