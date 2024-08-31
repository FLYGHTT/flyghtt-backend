package com.flyghtt.flyghtt_backend.repositories;

import com.flyghtt.flyghtt_backend.models.entities.BusinessCollaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BusinessCollaboratorRepository extends JpaRepository<BusinessCollaborator, UUID> {

    void deleteByBusinessIdAndUserId(UUID businessId, UUID userId);
    void deleteAllByBusinessId(UUID businessId);
}
