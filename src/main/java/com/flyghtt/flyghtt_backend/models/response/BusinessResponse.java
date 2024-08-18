package com.flyghtt.flyghtt_backend.models.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BusinessResponse {

    private UUID businessId;
    private String businessName;
    private String description;
}
