package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Data;

import java.util.List;

@Data
public class ColumnAllRequest {

    private String name;
    private String description;
    private List<String> factors;
}
