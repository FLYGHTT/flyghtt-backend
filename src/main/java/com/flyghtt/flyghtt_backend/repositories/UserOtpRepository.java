package com.flyghtt.flyghtt_backend.repositories;

import com.flyghtt.flyghtt_backend.models.entities.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {

    void deleteAllByUserId(UUID userId);
    Optional<UserOtp> findByUserId(UUID userId);
}
