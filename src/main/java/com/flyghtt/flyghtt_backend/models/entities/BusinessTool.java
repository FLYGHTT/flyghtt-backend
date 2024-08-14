package com.flyghtt.flyghtt_backend.models.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "business_tools")
public class BusinessTool {

    @Id
    @Builder.Default
    @Column(name = "business_tool_id")
    private UUID businessToolId = UUID.randomUUID();

    private UUID businessId;
    private UUID toolId;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "business_tools", joinColumns = @JoinColumn(name = "business_tool_id"))
//    @Column(name = "factor_name_value")
//    private Map<String, String> factorNameAndValueMap;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "businessTool")
    List<Factor> factors;
}
