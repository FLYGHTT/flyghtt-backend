package com.flyghtt.flyghtt_backend.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_otps")
public class UserOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int otp;
    private Date expiryDate;
    private UUID userId;
}
