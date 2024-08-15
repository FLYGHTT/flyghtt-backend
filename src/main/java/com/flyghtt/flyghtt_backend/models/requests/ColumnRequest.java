package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ColumnRequest {

    private String columnName;
    private String description;
    private List<String> factors;
}
