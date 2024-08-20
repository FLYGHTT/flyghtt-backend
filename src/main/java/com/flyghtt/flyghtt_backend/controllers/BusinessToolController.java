package com.flyghtt.flyghtt_backend.controllers;

import com.flyghtt.flyghtt_backend.models.entities.BusinessTool;
import com.flyghtt.flyghtt_backend.models.requests.BusinessToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.BusinessToolValueResponse;
import com.flyghtt.flyghtt_backend.services.BusinessToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("business-tools")
@RestController
public class BusinessToolController {

    private final BusinessToolService businessToolService;

    @GetMapping("{businessToolId}/values")
    public List<BusinessToolValueResponse> getBusinessToolValueResponseByBusinessTool(@PathVariable UUID businessToolId) {

        return businessToolService.getBusinessToolValueResponseByBusinessTool(businessToolId);
    }

    @PutMapping("{businessToolId}")
    public AppResponse updateBusinessTool(@PathVariable UUID businessToolId, @RequestBody BusinessToolRequest request) {

        return businessToolService.updateBusinessTool(businessToolId, request);
    }
}
