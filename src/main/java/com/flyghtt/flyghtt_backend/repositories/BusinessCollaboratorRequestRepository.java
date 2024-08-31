package com.flyghtt.flyghtt_backend.repositories;

import com.flyghtt.flyghtt_backend.models.entities.BusinessCollaboratorRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessCollaboratorRequestRepository extends JpaRepository<BusinessCollaboratorRequest, Long> {

    Optional<BusinessCollaboratorRequest> findByCollaboratorIdAndBusinessId(UUID collaboratorId, UUID businessId);
    void deleteAllByBusinessId(UUID businessId);
}
