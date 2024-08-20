package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.models.entities.BusinessToolValue;
import com.flyghtt.flyghtt_backend.models.response.BusinessToolValueResponse;
import com.flyghtt.flyghtt_backend.repositories.BusinessToolValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BusinessToolService {

    private final BusinessToolValueRepository businessToolValueRepository;

    public List<BusinessToolValueResponse> getBusinessToolValueResponseByBusinessTool(UUID businessToolId) {

        return businessToolValueRepository.findAllByBusinessToolId(businessToolId).parallelStream()
                .map(BusinessToolValue::toDto).collect(Collectors.toList());
    }
}
