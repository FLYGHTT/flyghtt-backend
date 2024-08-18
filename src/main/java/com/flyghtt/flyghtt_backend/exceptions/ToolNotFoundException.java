package com.flyghtt.flyghtt_backend.exceptions;

public class ToolNotFoundException extends EntityNotFoundException {
    public ToolNotFoundException() {
        super("TOOL");
    }
}
