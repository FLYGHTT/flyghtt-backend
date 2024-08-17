package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.models.requests.ToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.IdResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ToolService {

    public IdResponse createTool(ToolRequest request) {

        return null;
    }

    public ToolRequest getToolById(UUID toolId) {

        return null;
    }

    public List<ToolRequest> getAllToolsByUser() {

        return null;
    }

    @Transactional
    public AppResponse updateTool(ToolRequest request, UUID toolId) {

        return null;
    }

    public void throwErrorIfToolNameNotAvailable() {
    }
}
