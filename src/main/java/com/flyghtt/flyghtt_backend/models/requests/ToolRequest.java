package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Data;

@Data
public class ToolRequest {

    private String toolName;
    private String toolDescription;

    private boolean isPublic;
}
