package com.flyghtt.flyghtt_backend.repositories;

import com.flyghtt.flyghtt_backend.models.entities.BusinessToolValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessToolValueRepository extends JpaRepository<BusinessToolValue, Long> {

    List<BusinessToolValue> findAllByBusinessToolId(UUID businessToolId);
    Optional<BusinessToolValue> findByFactorIdAndBusinessToolId(UUID factorId, UUID businessToolId);
    void deleteAllByBusinessToolId(UUID businessToolId);
}
