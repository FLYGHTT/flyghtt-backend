package com.flyghtt.flyghtt_backend.exceptions;

public class FactorNotFoundException extends EntityNotFoundException {
    public FactorNotFoundException() {
        super("FACTOR");
    }
}
