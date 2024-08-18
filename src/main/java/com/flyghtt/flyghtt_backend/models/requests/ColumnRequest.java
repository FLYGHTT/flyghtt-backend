package com.flyghtt.flyghtt_backend.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColumnRequest {

    @NotNull
    private String columnName;
    private String description;
}
