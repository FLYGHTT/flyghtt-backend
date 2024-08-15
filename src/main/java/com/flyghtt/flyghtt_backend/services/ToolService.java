package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.models.entities.Tool;
import com.flyghtt.flyghtt_backend.models.entities.ToolColumn;
import com.flyghtt.flyghtt_backend.models.requests.ColumnRequest;
import com.flyghtt.flyghtt_backend.models.requests.ToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.repositories.ToolRepository;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ToolService {

    private final ToolRepository toolRepository;

    public AppResponse createTool(ToolRequest request) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        Tool tool = request.toDb();

        toolRepository.save(tool);

        return AppResponse.builder()
                .message("Tool has been successfully created")
                .status(HttpStatus.CREATED)
                .build();
    }

    public ToolRequest getToolById(UUID toolId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        return toolRepository.findByToolId(toolId).orElseThrow().toDto();
    }
}
