package com.flyghtt.flyghtt_backend.models.entities;

import com.flyghtt.flyghtt_backend.models.response.BusinessResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "businesses")
public class Business {

    @Id
    @Builder.Default
    private UUID businessId = UUID.randomUUID();

    private String name;
    private String description;

    @Builder.Default
    private Instant createdAt = Instant.now();
    private UUID createdBy;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "business_employees",
            joinColumns = @JoinColumn(name = "business_id", referencedColumnName = "businessId"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "userId")
    )
    private List<User> employees;


    public BusinessResponse toDto() {

        return BusinessResponse.builder()
                .businessId(businessId)
                .businessName(name)
                .description(description)
                .build();
    }
}
