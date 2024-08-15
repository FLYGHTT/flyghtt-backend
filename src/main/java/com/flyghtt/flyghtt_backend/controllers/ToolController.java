package com.flyghtt.flyghtt_backend.controllers;


import com.flyghtt.flyghtt_backend.models.requests.ToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.services.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("tools")
@RestController
public class ToolController {

    private final ToolService toolService;

    @PostMapping
    public AppResponse createTool(ToolRequest request) {

        return toolService.createTool(request);
    }
}
