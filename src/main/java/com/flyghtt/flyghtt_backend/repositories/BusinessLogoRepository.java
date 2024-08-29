package com.flyghtt.flyghtt_backend.repositories;

import com.flyghtt.flyghtt_backend.models.entities.BusinessLogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessLogoRepository extends JpaRepository<BusinessLogo, UUID> {

    Optional<BusinessLogo> findByBusinessId(UUID businessId);

    void deleteByBusinessId(UUID businessId);
}
