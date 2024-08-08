package com.flyghtt.flyghtt_backend.models.requests;


import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;
}
