package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BusinessToolValueRequest {

    private UUID factorId;
    private String value;
}
