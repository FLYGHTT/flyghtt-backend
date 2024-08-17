package com.flyghtt.flyghtt_backend.controllers;


import com.flyghtt.flyghtt_backend.models.entities.Column;
import com.flyghtt.flyghtt_backend.models.requests.ColumnRequest;
import com.flyghtt.flyghtt_backend.models.requests.ToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.ColumnResponse;
import com.flyghtt.flyghtt_backend.models.response.IdResponse;
import com.flyghtt.flyghtt_backend.models.response.ToolResponse;
import com.flyghtt.flyghtt_backend.services.ColumnService;
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
    private final ColumnService columnService;

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

    @PostMapping("{toolId}/columns")
    public IdResponse createColumn(@PathVariable UUID toolId, @RequestBody ColumnRequest request) {

        return toolService.createColumn(toolId, request);
    }

    @GetMapping("{toolId}/columns")
    public List<ColumnResponse> getAllToolColumns(@PathVariable UUID toolId) {

        return toolService.getAllToolColumns(toolId);
    }

    @GetMapping("columns/{columnId}")
    public Column getColumnById(@PathVariable UUID columnId) {

        return columnService.getByColumnId(columnId);
    }

    @PutMapping("columns/{columnId}")
    public AppResponse updateColumn(@PathVariable UUID columnId, @RequestBody ColumnRequest request) {

        return columnService.updateColumn(columnId, request);
    }

    @DeleteMapping("columns/{columnId}")
    public AppResponse deleteColumn(@PathVariable UUID columnId) {

        return columnService.deleteColumn(columnId);
    }
}
