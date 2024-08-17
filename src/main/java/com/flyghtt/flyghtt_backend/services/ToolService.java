package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.exceptions.ToolNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.Tool;
import com.flyghtt.flyghtt_backend.models.requests.ToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.IdResponse;
import com.flyghtt.flyghtt_backend.models.response.ToolResponse;
import com.flyghtt.flyghtt_backend.repositories.ToolRepository;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ToolService {

    private final ToolRepository toolRepository;

    public IdResponse createTool(ToolRequest request) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        Tool tool = request.toDb();
        toolRepository.save(tool);

        return IdResponse.builder()
                .id(tool.getToolId())
                .message("Tool Has been successfully created")
                .build();
    }

    public ToolResponse getToolById(UUID toolId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        return toolRepository.findByToolId(toolId).orElseThrow(ToolNotFoundException::new).toDto();
    }

    public List<ToolResponse> getAllToolsByUser() {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        return toolRepository.findAllByCreatedBy(UserUtil.getLoggedInUser().get().getUserId())
                .parallelStream().map(Tool::toDto).collect(Collectors.toList());
    }

    @Transactional
    public AppResponse updateTool(ToolRequest request, UUID toolId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        Tool tool = toolRepository.findByToolId(toolId).orElseThrow(ToolNotFoundException::new);
        tool.setName(request.getToolName());
        tool.setDescription(request.getToolDescription());
        tool.setLink(request.getLink());
        tool.setPublic(request.isPublic());
        tool.setCommentable(request.isCommentable());

        toolRepository.save(tool);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Tool has been successfully updated").build();
    }

    @Transactional
    public AppResponse deleteTool(UUID toolId) {

        toolRepository.deleteByToolId(toolId);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Tool has been successfully deleted").build();
    }
}
