package com.flyghtt.flyghtt_backend.models.entities;

import com.flyghtt.flyghtt_backend.models.response.BusinessResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;
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

    @OneToMany(mappedBy = "businessCollaboratorId", fetch = FetchType.EAGER)
    private Set<BusinessCollaborator> collaborators;

    @OneToMany(mappedBy = "businessId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BusinessTool> businessTools;


    public BusinessResponse toDto(byte[] businessLogoImageData) {

        return BusinessResponse.builder()
                .businessId(businessId)
                .businessName(name)
                .description(description)
                .numberOfEmployees(collaborators.size())
                .createdAt(createdAt)
                .numberOfBusinessTools(businessTools.size())
                .businessLogoImageData(businessLogoImageData)
                .build();
    }
}
