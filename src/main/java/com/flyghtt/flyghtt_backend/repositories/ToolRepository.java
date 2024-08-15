package com.flyghtt.flyghtt_backend.repositories;

import com.flyghtt.flyghtt_backend.models.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
}
