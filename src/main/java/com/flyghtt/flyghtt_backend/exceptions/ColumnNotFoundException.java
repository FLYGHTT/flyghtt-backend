package com.flyghtt.flyghtt_backend.exceptions;

public class ColumnNotFoundException extends EntityNotFoundException {
    public ColumnNotFoundException() {
        super("COLUMN");
    }
}
