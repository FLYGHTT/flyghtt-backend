package com.flyghtt.flyghtt_backend.models.requests;


import lombok.Data;

@Data
public class PasswordResetRequest {

    private String email;
    private int otp;
    private String newPassword;
    private String confirmNewPassword;
}
