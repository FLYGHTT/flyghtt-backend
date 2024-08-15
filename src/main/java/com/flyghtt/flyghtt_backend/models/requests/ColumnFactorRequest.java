package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Data;

import java.util.List;

@Data
public class ColumnFactorRequest {

    private String columnName;
    private List<String> factors;
}
