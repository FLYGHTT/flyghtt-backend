package com.flyghtt.flyghtt_backend.models.requests;

import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class AddEmployeeRequest {

    private Set<UUID> employeeIds;
}
