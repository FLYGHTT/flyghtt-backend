package com.flyghtt.flyghtt_backend.repositories;

import com.flyghtt.flyghtt_backend.models.entities.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    Optional<Business> findByBusinessId(UUID businessId);
    List<Business> findAllByCreatedBy(UUID createdBy);

    Optional<Business> findByBusinessIdAndCreatedBy(UUID businessId, UUID createdBy);

    void deleteByBusinessIdAndCreatedBy(UUID businessId, UUID createdBy);

    void deleteAllByCreatedBy(UUID createdBy);
}
