package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.exceptions.BusinessToolNotFoundException;
import com.flyghtt.flyghtt_backend.exceptions.BusinessToolValueNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.BusinessTool;
import com.flyghtt.flyghtt_backend.models.requests.BusinessToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.BusinessToolValueResponse;
import com.flyghtt.flyghtt_backend.repositories.BusinessToolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BusinessToolService {

    private final BusinessToolRepository businessToolRepository;

    @Transactional
    public AppResponse updateBusinessTool(UUID businessToolId, BusinessToolRequest request) {

//        BusinessTool businessTool = businessToolRepository.findByBusinessToolId(businessToolId).orElseThrow(BusinessToolNotFoundException::new);
//
//        businessTool.setName(request.getBusinessToolName());
//
//        businessToolRepository.save(businessTool);
//
//        List<BusinessToolValue> businessToolValues = new ArrayList<>();
//
//        request.getToolValues().forEach(
//                toolValue -> {
//
//                    BusinessToolValue businessToolValue = businessToolValueRepository.findByFactorIdAndBusinessToolId(toolValue.getFactorId(), businessToolId).orElseThrow(BusinessToolValueNotFoundException::new);
//                    businessToolValue.setValue(toolValue.getValue());
//                    businessToolValues.add(businessToolValue);
//                }
//        );
//
//        businessToolValueRepository.saveAll(businessToolValues);
//
//        return AppResponse.builder()
//                .status(HttpStatus.OK)
//                .message("Business Tool has been successfully edited")
//                .build();

        return null;
    }

    @Transactional
    public AppResponse deleteBusinessTool(UUID businessToolId) {

        return null;

//        businessToolValueRepository.deleteAllByBusinessToolId(businessToolId);
//        businessToolRepository.deleteByBusinessToolId(businessToolId);
//
//        return AppResponse.builder()
//                .status(HttpStatus.OK)
//                .message("Business Tool has been successfully deleted").build();
    }

    @Transactional
    public void deleteBusinessToolsByBusinessId(UUID businessId) {

        businessToolRepository.findAllByBusinessId(businessId).forEach(
                businessTool -> deleteBusinessTool(businessTool.getBusinessToolId())
        );
        businessToolRepository.deleteAllByBusinessId(businessId);
    }
}
