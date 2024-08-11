package com.flyghtt.flyghtt_backend.exceptions;

public class OtpNotFoundException extends EntityNotFoundException {
    public OtpNotFoundException() {
        super("OTP");
    }
}
