package com.flyghtt.flyghtt_backend.controllers.utils;


import com.flyghtt.flyghtt_backend.exceptions.EntityNotFoundException;

import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppResponse handleUserNotFoundException(EntityNotFoundException exception) {

        log.error("An error occurred while handling your request " + exception);
        return AppResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AppResponse handleGenericException(Exception exception) {

        log.error("An error occurred while handling your request " + exception);
        return AppResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
    }
}
