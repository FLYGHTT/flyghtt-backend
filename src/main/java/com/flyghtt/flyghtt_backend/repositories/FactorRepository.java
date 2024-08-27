package com.flyghtt.flyghtt_backend.repositories;

import com.flyghtt.flyghtt_backend.models.entities.Factor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FactorRepository extends JpaRepository<Factor, UUID> {

    List<Factor> findAllByColumnId(UUID columnId);
    Optional<Factor> findByFactorId(UUID factorId);
    void deleteByFactorId(UUID factorId);
    void deleteAllByColumnId(UUID columnId);
}
