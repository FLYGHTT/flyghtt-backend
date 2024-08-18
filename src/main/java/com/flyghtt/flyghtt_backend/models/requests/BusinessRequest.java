package com.flyghtt.flyghtt_backend.models.requests;


import lombok.Builder;
import lombok.Data;

@Data
public class BusinessRequest {

    private String businessName;
    private String description;
}
