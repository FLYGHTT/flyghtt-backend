package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Data;

import java.util.List;

@Data
public class ToolAllRequest {

    private String name;
    private String description;
    private String link;
    private boolean commentable;
    private boolean isPublic;

    private List<ColumnAllRequest> columns;
}
