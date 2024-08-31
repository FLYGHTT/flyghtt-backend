package com.flyghtt.flyghtt_backend.exceptions;

public class BusinessCollaboratorRequestNotFoundException extends EntityNotFoundException {
    public BusinessCollaboratorRequestNotFoundException() {
        super("REQUEST TO JOIN BUSINESS");
    }
}
