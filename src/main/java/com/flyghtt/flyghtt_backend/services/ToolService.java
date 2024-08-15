package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.models.entities.ColumnFactor;
import com.flyghtt.flyghtt_backend.models.entities.Tool;
import com.flyghtt.flyghtt_backend.models.entities.ToolColumn;
import com.flyghtt.flyghtt_backend.models.requests.ToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.repositories.ToolRepository;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ToolService {

    private final ToolRepository toolRepository;

    @Transactional
    public AppResponse createTool(ToolRequest request) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        Tool tool = Tool.builder()
                .name(request.getToolName())
                .description(request.getToolDescription())
                .link(request.getLink())
                .createdBy(UserUtil.getLoggedInUser().get().getUserId())
                .build();

        System.out.println(tool.getToolId());

        List<ToolColumn> toolColumns = request.getColumns().stream().map(
                column -> {
                    ToolColumn toolColumn = ToolColumn.builder()
                            .name(column.getColumnName())
                            .description(column.getDescription())
                            .toolId(tool.getToolId())
                            .build();

                    System.out.println(toolColumn.getColumnId());

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

        toolRepository.save(tool);

        return AppResponse.builder()
                .message("Tool has been successfully created")
                .status(HttpStatus.CREATED)
                .build();
    }
}
