package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.models.requests.ToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.repositories.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ToolService {

    private final ToolRepository toolRepository;

    public AppResponse createTool(ToolRequest request) {

        return null;
    }
}
