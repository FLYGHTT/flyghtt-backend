package com.flyghtt.flyghtt_backend.models.requests;

import com.flyghtt.flyghtt_backend.models.entities.ColumnFactor;
import com.flyghtt.flyghtt_backend.models.entities.Tool;
import com.flyghtt.flyghtt_backend.models.entities.ToolColumn;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ToolRequest {

    private String toolName;
    private String toolDescription;
    private String link;
    private boolean commentable;
    private boolean isPublic;

    private List<ColumnRequest> columns;

    public Tool toDb() {

        Tool tool = Tool.builder()
                .name(toolName)
                .description(toolDescription)
                .link(link)
                .createdBy(UserUtil.getLoggedInUser().get().getUserId())
                .commentable(commentable)
                .isPublic(isPublic)
                .build();

        List<ToolColumn> toolColumns = columns.stream().map(
                column -> {
                    ToolColumn toolColumn = ToolColumn.builder()
                            .name(column.getColumnName().toUpperCase())
                            .description(column.getDescription())
                            .toolId(tool.getToolId())
                            .build();

                    toolColumn.setColumnFactors(
                            column.getFactors().stream().map(
                                    factor -> ColumnFactor.builder()
                                            .columnId(toolColumn.getColumnId())
                                            .factorName(factor)
                                            .build()
                            ).collect(Collectors.toList())
                    );

                    return toolColumn;
                }).toList();

        tool.setColumns(toolColumns);

        return tool;
    }
}
