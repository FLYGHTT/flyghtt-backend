package com.flyghtt.flyghtt_backend.exceptions;

public class EmailAlreadyExistsException extends DataIntegrityViolationException {
    public EmailAlreadyExistsException() {
        super("EMAIL");
    }
}
