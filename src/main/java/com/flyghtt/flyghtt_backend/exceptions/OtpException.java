package com.flyghtt.flyghtt_backend.exceptions;

public class OtpException extends FlyghttException{

    public OtpException () {

        super("OTP not valid");
    }
}
