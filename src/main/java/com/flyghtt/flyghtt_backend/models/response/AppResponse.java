package com.flyghtt.flyghtt_backend.models.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class AppResponse {

    private String message;
    private HttpStatus status;
}
