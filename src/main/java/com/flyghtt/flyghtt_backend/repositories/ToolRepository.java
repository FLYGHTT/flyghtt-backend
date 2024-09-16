package com.flyghtt.flyghtt_backend.repositories;

import com.flyghtt.flyghtt_backend.models.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToolRepository extends JpaRepository<Tool, UUID> {

    Optional<Tool> findByToolId(UUID toolId);
    boolean existsByCreatedBy(UUID createdBy);
    List<Tool> findAllByCreatedBy(UUID createdBy);
    void deleteByToolId(UUID toolId);
    boolean existsByNameAndIsPublicTrueAndToolIdNot(String name, UUID toolId);
}
