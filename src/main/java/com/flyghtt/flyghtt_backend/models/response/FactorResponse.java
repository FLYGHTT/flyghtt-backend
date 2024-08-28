package com.flyghtt.flyghtt_backend.models.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FactorResponse {

    private String factorName;
    private String value;
}
