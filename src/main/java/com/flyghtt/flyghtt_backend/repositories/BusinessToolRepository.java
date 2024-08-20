package com.flyghtt.flyghtt_backend.repositories;

import com.flyghtt.flyghtt_backend.models.entities.BusinessTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessToolRepository extends JpaRepository<BusinessTool, Long> {

    Optional<BusinessTool> findByBusinessToolId(UUID businessToolId);
    void deleteByBusinessToolId(UUID businessToolId);
}
