package com.flyghtt.flyghtt_backend.controllers;


import com.flyghtt.flyghtt_backend.models.requests.ToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.IdResponse;
import com.flyghtt.flyghtt_backend.models.response.ToolResponse;
import com.flyghtt.flyghtt_backend.services.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RequestMapping("tools")
@RestController
public class ToolController {

    private final ToolService toolService;

    @PostMapping
    public IdResponse createTool(@RequestBody ToolRequest request) {

        return toolService.createTool(request);
    }

    @GetMapping("{toolId}")
    public ToolResponse getToolById(@PathVariable UUID toolId) {

        return toolService.getToolById(toolId);
    }

    @GetMapping
    public List<ToolResponse> getAllToolsByUser() {

        return toolService.getAllToolsByUser();
    }

    @PutMapping("{toolId}")
    public AppResponse updateTool(@RequestBody ToolRequest request, @PathVariable UUID toolId) {

        return toolService.updateTool(request, toolId);
    }

    @DeleteMapping("{toolId}")
    public AppResponse deleteTool(@PathVariable UUID toolId) {

        return toolService.deleteTool(toolId);
    }
}
