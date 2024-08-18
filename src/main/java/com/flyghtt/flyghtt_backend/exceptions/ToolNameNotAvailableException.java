package com.flyghtt.flyghtt_backend.exceptions;

public class ToolNameNotAvailableException extends FlyghttException {
    public ToolNameNotAvailableException() {
        super("NAME NOT AVAILABLE (Choose another tool name)");
    }
}
