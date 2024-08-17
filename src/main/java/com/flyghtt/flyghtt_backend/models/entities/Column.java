package com.flyghtt.flyghtt_backend.models.entities;

import com.flyghtt.flyghtt_backend.models.response.ColumnResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "columns")
public class Column {

    @Id
    @Builder.Default private UUID columnId = UUID.randomUUID();

    private String name;
    private String description;

    private UUID toolId;

    public ColumnResponse toDto() {

        return ColumnResponse.builder()
                .columnId(columnId)
                .columnName(name.toUpperCase())
                .columnDescription(description)
                .build();
    }
}
