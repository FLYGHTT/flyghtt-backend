package com.flyghtt.flyghtt_backend.exceptions;

public class UnauthorizedException extends FlyghttException {
    public UnauthorizedException(String reason) {
        super("Unable to access resource due to " + reason);
    }
}
