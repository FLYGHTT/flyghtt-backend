package com.flyghtt.flyghtt_backend.controllers;

import com.flyghtt.flyghtt_backend.models.entities.BusinessTool;
import com.flyghtt.flyghtt_backend.models.requests.BusinessToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.IdResponse;
import com.flyghtt.flyghtt_backend.services.BusinessToolService;
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

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("business-tools")
@RestController
public class BusinessToolController {

    private final BusinessToolService businessToolService;

    @PostMapping("{businessId}/{toolId}")
    public IdResponse createBusinessTool(@PathVariable UUID businessId, @PathVariable UUID toolId, @RequestBody BusinessToolRequest request) {

        return businessToolService.createBusinessTool(businessId, toolId, request);
    }

    @GetMapping("{businessToolId}")
    public BusinessTool getBusinessTool(@PathVariable UUID businessToolId) {

        return businessToolService.getBusinessToolById(businessToolId);
    }

    @Operation(summary = "update business tool values")
    @PutMapping("{businessToolId}")
    public AppResponse updateBusinessTool(@PathVariable UUID businessToolId, @RequestBody BusinessToolRequest request) {

        return businessToolService.updateBusinessTool(businessToolId, request);
    }

    @Operation(summary = "delete business tool with values")
    @DeleteMapping("{businessToolId}")
    public AppResponse deleteBusinessTool(@PathVariable UUID businessToolId) {

        return businessToolService.deleteBusinessTool(businessToolId);
    }
}
