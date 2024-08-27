package com.flyghtt.flyghtt_backend.services;


import com.flyghtt.flyghtt_backend.exceptions.ToolCommentNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.ToolComment;
import com.flyghtt.flyghtt_backend.models.requests.ToolCommentRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.IdResponse;
import com.flyghtt.flyghtt_backend.repositories.ToolCommentRepository;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ToolCommentService {

    private final ToolCommentRepository toolCommentRepository;

    public IdResponse createToolComment(ToolCommentRequest request, UUID toolId) {

        ToolComment toolComment = ToolComment.builder()
                .comment(request.getComment())
                .toolId(toolId)
                .userId(UserUtil.getLoggedInUser().get().getUserId())
                .build();

        toolCommentRepository.save(toolComment);

        return IdResponse.builder()
                .message("Comment successfully added (Tool Comment Id)")
                .id(toolComment.getToolCommentId()).build();
    }

    public AppResponse updateToolComment(ToolCommentRequest request, UUID toolCommentId) {

        ToolComment toolComment = toolCommentRepository.findByToolCommentIdAndUserId(toolCommentId, UserUtil.getLoggedInUser().get().getUserId()).orElseThrow(ToolCommentNotFoundException::new);

        toolComment.setComment(request.getComment());
        toolComment.setUpdatedAt(Instant.now());

        toolCommentRepository.save(toolComment);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Comment successfully updated").build();
    }

    public List<ToolComment> getAllToolCommentsByToolId(UUID toolId) {

        return toolCommentRepository.findAllByToolId(toolId);
    }

    public List<ToolComment> getAllToolCommentsByUserId(UUID userId) {

        return toolCommentRepository.findAllByUserId(userId);
    }

    public ToolComment getToolCommentById(UUID toolCommentId) {

        return toolCommentRepository.findById(toolCommentId).orElseThrow(ToolCommentNotFoundException::new);
    }

    @Transactional
    public AppResponse deleteToolComment(UUID toolCommentId) {

        toolCommentRepository.deleteById(toolCommentId);
        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Comment has been successfully deleted").build();
    }

    @Transactional
    public void deleteByToolId(UUID toolId) {

        toolCommentRepository.deleteAllByToolId(toolId);
    }
}
