package com.flyghtt.flyghtt_backend.repositories;

import com.flyghtt.flyghtt_backend.models.entities.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ColumnRepository extends JpaRepository<Column, UUID> {

    Optional<Column> findByColumnId(UUID columnId);
    List<Column> findAllByToolId(UUID toolId);
    void deleteByColumnId(UUID columnId);
    void deleteAllByToolId(UUID toolId);
}
