package com.flyghtt.flyghtt_backend.exceptions;

public class EntityNotFoundException extends ClientSideException {

    public EntityNotFoundException (String ENTITY) {

        super(ENTITY + " NOT FOUND");
    }
}
