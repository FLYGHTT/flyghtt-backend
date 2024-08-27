package com.flyghtt.flyghtt_backend.repositories;

import com.flyghtt.flyghtt_backend.models.entities.ToolComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ToolCommentRepository extends JpaRepository<ToolComment, UUID> {

    Optional<ToolComment> findByToolCommentIdAndUserId(UUID toolCommentId, UUID userId);
    List<ToolComment> findAllByToolId(UUID toolId);
    List<ToolComment> findAllByUserId(UUID userId);
    void deleteAllByToolId(UUID toolId);
}
