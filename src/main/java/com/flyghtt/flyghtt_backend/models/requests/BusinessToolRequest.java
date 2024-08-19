package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class BusinessToolRequest {

    private UUID factorId;
    private String value;
}
