package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.exceptions.BusinessToolNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.BusinessTool;
import com.flyghtt.flyghtt_backend.models.requests.BusinessToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.IdResponse;
import com.flyghtt.flyghtt_backend.repositories.BusinessToolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BusinessToolService {

    private final BusinessToolRepository businessToolRepository;

    public IdResponse createBusinessTool(UUID businessId, UUID toolId, BusinessToolRequest request) {

        BusinessTool businessTool = BusinessTool.builder()
                .businessId(businessId)
                .toolId(toolId)
                .name(request.getBusinessToolName())
                .content(request.getContent()).build();

        businessToolRepository.save(businessTool);

        return IdResponse.builder()
                .message("Business Tool successfully created (Business Tool Id)")
                .id(businessTool.getBusinessToolId()).build();
    }



    @Transactional
    public AppResponse updateBusinessTool(UUID businessToolId, BusinessToolRequest request) {

        BusinessTool toBeUpdated = businessToolRepository.findByBusinessToolId(businessToolId).orElseThrow(BusinessToolNotFoundException::new);

        toBeUpdated.setName(request.getBusinessToolName());
        toBeUpdated.setContent(request.getContent());

        businessToolRepository.save(toBeUpdated);

        return AppResponse.builder()
                .message("Business Tool has been successfully updated")
                .status(HttpStatus.OK).build();
    }

    @Transactional
    public AppResponse deleteBusinessTool(UUID businessToolId) {

        businessToolRepository.deleteByBusinessToolId(businessToolId);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Business Tool has been successfully deleted").build();
    }

    @Transactional
    public void deleteBusinessToolsByBusinessId(UUID businessId) {

        businessToolRepository.deleteAllByBusinessId(businessId);
    }

    public BusinessTool getBusinessToolById(UUID businessToolId) {

        return businessToolRepository.findByBusinessToolId(businessToolId).orElseThrow(BusinessToolNotFoundException::new);
    }
}
