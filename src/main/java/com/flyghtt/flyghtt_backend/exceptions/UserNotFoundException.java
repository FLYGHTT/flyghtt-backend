package com.flyghtt.flyghtt_backend.exceptions;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException () {

        super("USER");
    }
}
