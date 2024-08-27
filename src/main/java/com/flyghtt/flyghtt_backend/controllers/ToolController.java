package com.flyghtt.flyghtt_backend.controllers;


import com.flyghtt.flyghtt_backend.models.entities.Column;
import com.flyghtt.flyghtt_backend.models.entities.Factor;
import com.flyghtt.flyghtt_backend.models.entities.ToolComment;
import com.flyghtt.flyghtt_backend.models.requests.ColumnRequest;
import com.flyghtt.flyghtt_backend.models.requests.FactorRequest;
import com.flyghtt.flyghtt_backend.models.requests.FavouriteRequest;
import com.flyghtt.flyghtt_backend.models.requests.LikeRequest;
import com.flyghtt.flyghtt_backend.models.requests.ToolAllRequest;
import com.flyghtt.flyghtt_backend.models.requests.ToolCommentRequest;
import com.flyghtt.flyghtt_backend.models.requests.ToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.ColumnResponse;
import com.flyghtt.flyghtt_backend.models.response.IdResponse;
import com.flyghtt.flyghtt_backend.models.response.ToolResponse;
import com.flyghtt.flyghtt_backend.services.ColumnService;
import com.flyghtt.flyghtt_backend.services.FactorService;
import com.flyghtt.flyghtt_backend.services.OneFactorRequest;
import com.flyghtt.flyghtt_backend.services.ToolCommentService;
import com.flyghtt.flyghtt_backend.services.ToolService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final FactorService factorService;
    private final ToolCommentService toolCommentService;

    @Operation(summary = "Create Tool")
    @PostMapping
    public IdResponse createTool(@RequestBody ToolRequest request) {

        return toolService.createTool(request);
    }

    @Operation(summary = "Create tools with columns all at once")
    @PostMapping("all")
    public IdResponse createTool(@RequestBody ToolAllRequest request) {

        return toolService.createToolAll(request);
    }

    @Operation(summary = "Get tool by Id")
    @GetMapping("{toolId}")
    public ToolResponse getToolById(@PathVariable UUID toolId) {

        return toolService.getToolById(toolId);
    }

    @Operation(summary = "Get all user tools")
    @GetMapping
    public List<ToolResponse> getAllToolsByUser() {

        return toolService.getAllToolsByUser();
    }

    @Operation(summary = "Update tool details")
    @PutMapping("{toolId}")
    public AppResponse updateTool(@RequestBody ToolRequest request, @PathVariable UUID toolId) {

        return toolService.updateTool(request, toolId);
    }

    @Operation(summary = "Delete Tool by Id")
    @DeleteMapping("{toolId}")
    public AppResponse deleteTool(@PathVariable UUID toolId) {

        return toolService.deleteTool(toolId);
    }

    @Operation(summary = "Create Tool Column")
    @PostMapping("{toolId}/columns")
    public IdResponse createColumn(@PathVariable UUID toolId, @RequestBody ColumnRequest request) {

        return toolService.createColumn(toolId, request);
    }

    @Operation(summary = "Get all tool columns")
    @GetMapping("{toolId}/columns")
    public List<ColumnResponse> getAllToolColumns(@PathVariable UUID toolId) {

        return toolService.getAllToolColumns(toolId);
    }

    @Operation(summary = "Get column by Id")
    @GetMapping("columns/{columnId}")
    public Column getColumnById(@PathVariable UUID columnId) {

        return columnService.getByColumnId(columnId);
    }

    @Operation(summary = "Update column details")
    @PutMapping("columns/{columnId}")
    public AppResponse updateColumn(@PathVariable UUID columnId, @RequestBody ColumnRequest request) {

        return columnService.updateColumn(columnId, request);
    }

    @Operation(summary = "Delete Column by Id")
    @DeleteMapping("columns/{columnId}")
    public AppResponse deleteColumn(@PathVariable UUID columnId) {

        return columnService.deleteColumn(columnId);
    }

    @Operation(summary = "Create Factor for tool's column")
    @PostMapping("columns/{columnId}/factors")
    public AppResponse createFactor(@PathVariable UUID columnId, @RequestBody FactorRequest request) {

        return columnService.createFactor(columnId, request);
    }

    @Operation(summary = "Get all factors for a tool's column")
    @GetMapping("columns/{columnId}/factors")
    public List<Factor> getAllColumnFactors(@PathVariable UUID columnId) {

        return factorService.getAllColumnFactors(columnId);
    }

    @Operation(summary = "Update factor name")
    @PutMapping("columns/factors/{factorId}")
    public AppResponse updateFactor(@PathVariable UUID factorId, @RequestBody OneFactorRequest request) {

        return factorService.updateFactor(factorId, request);
    }

    @Operation(summary = "Delete factor")
    @DeleteMapping("columns/factors/{factorId}")
    public AppResponse deleteFactor(@PathVariable UUID factorId) {

        return factorService.deleteFactor(factorId);
    }

    @Operation(summary = "Like tool")
    @PostMapping("{toolId}/like")
    public AppResponse likeTool(@PathVariable UUID toolId, @RequestBody LikeRequest request) {

        return toolService.likeTool(toolId, request);
    }

    @Operation(summary = "add tool to favourites")
    @PostMapping("{toolId}/favourites")
    public AppResponse addToFavourites(@PathVariable UUID toolId, @RequestBody FavouriteRequest request) {

        return toolService.addToFavourites(toolId, request);
    }

    @Operation(summary = "comment on tool")
    @PostMapping("{toolId}/comments")
    public IdResponse createToolComment(@RequestBody ToolCommentRequest request, @PathVariable UUID toolId) {

        return toolCommentService.createToolComment(request, toolId);
    }

    @Operation(summary = "update comment")
    @PutMapping("comments/{toolCommendId}")
    public AppResponse updateToolComment(@RequestBody ToolCommentRequest request, @PathVariable UUID toolCommendId) {

        return toolCommentService.updateToolComment(request, toolCommendId);
    }

    @Operation(summary = "get all tool comments")
    @GetMapping("{toolId}/comments")
    public List<ToolComment> getAllToolCommentsByToolId(@PathVariable UUID toolId) {

        return toolCommentService.getAllToolCommentsByToolId(toolId);
    }

    @Operation(summary = "get all comments by user")
    @GetMapping("comments/user/{userId}")
    public List<ToolComment> getAllToolCommentsByUser(@PathVariable UUID userId) {

        return toolCommentService.getAllToolCommentsByUserId(userId);
    }

    @Operation(summary = "get comment by comment id")
    @GetMapping("comments/{toolCommentId}")
    public ToolComment getToolCommentById(@PathVariable UUID toolCommentId) {

        return toolCommentService.getToolCommentById(toolCommentId);
    }

    @Operation(summary = "delete comment by id")
    @DeleteMapping("comments/{toolCommentId}")
    public AppResponse deleteToolComment(@PathVariable UUID toolCommentId) {

        return toolCommentService.deleteToolComment(toolCommentId);
    }
}
