package com.flyghtt.flyghtt_backend.exceptions;

public class DataIntegrityViolationException extends FlyghttException {
    public DataIntegrityViolationException(String entity) {
        super(entity + " ALREADY EXISTS");
    }
}
