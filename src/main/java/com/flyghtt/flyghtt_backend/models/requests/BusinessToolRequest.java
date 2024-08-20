package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Data;

import java.util.List;

@Data
public class BusinessToolRequest {

    private String businessToolName;
    private List<BusinessToolValueRequest> toolValues;
}
