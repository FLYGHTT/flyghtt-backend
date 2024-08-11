package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
