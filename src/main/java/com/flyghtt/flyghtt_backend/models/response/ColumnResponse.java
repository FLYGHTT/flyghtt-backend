package com.flyghtt.flyghtt_backend.models.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ColumnResponse {

    private UUID columnId;
    private String columnName;
    private String columnDescription;
}
