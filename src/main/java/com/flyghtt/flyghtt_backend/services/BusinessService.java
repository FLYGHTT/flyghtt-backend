package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.exceptions.BusinessNotFoundException;
import com.flyghtt.flyghtt_backend.exceptions.UnauthorizedException;
import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.Business;
import com.flyghtt.flyghtt_backend.models.entities.BusinessTool;
import com.flyghtt.flyghtt_backend.models.entities.BusinessToolValue;
import com.flyghtt.flyghtt_backend.models.entities.User;
import com.flyghtt.flyghtt_backend.models.requests.AddEmployeeRequest;
import com.flyghtt.flyghtt_backend.models.requests.BusinessRequest;
import com.flyghtt.flyghtt_backend.models.requests.BusinessToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.BusinessResponse;
import com.flyghtt.flyghtt_backend.models.response.IdResponse;
import com.flyghtt.flyghtt_backend.repositories.BusinessRepository;
import com.flyghtt.flyghtt_backend.repositories.BusinessToolRepository;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final UserService userService;
    private final BusinessToolRepository businessToolRepository;

    public BusinessResponse createBusiness(BusinessRequest request) throws UserNotFoundException {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        User user = UserUtil.getLoggedInUser().get();

        Business business = Business.builder()
                .name(request.getBusinessName().toUpperCase())
                .description(request.getDescription())
                .createdBy(user.getUserId())
                .createdAt(Instant.now())
                .build();

        return businessRepository.save(business).toDto();
    }

    public BusinessResponse getByBusinessId(UUID businessId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        return getBusinessById(businessId);
    }

    public List<BusinessResponse> getAllUserBusinesses() {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        UUID userId = UserUtil.getLoggedInUser().get().getUserId();

        return businessRepository.findAllByCreatedBy(userId).parallelStream().map(Business::toDto)
                .collect(Collectors.toList());
    }

    public BusinessResponse updateBusinessDetails(UUID businessId, BusinessRequest request) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        UUID userId = UserUtil.getLoggedInUser().get().getUserId();
        Business business = businessRepository.findByBusinessIdAndCreatedBy(businessId, userId).orElseThrow(BusinessNotFoundException::new);

        business.setName(request.getBusinessName().toUpperCase());
        business.setDescription(request.getDescription());

        return businessRepository.save(business).toDto();
    }

    @Transactional
    public AppResponse deleteBusiness(UUID businessId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        UUID userId = UserUtil.getLoggedInUser().get().getUserId();

        businessRepository.deleteByBusinessIdAndCreatedBy(businessId, userId);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Business has been successfully deleted")
                .build();
    }

    @Transactional
    public AppResponse deleteAllUserBusinesses() {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        UUID userId = UserUtil.getLoggedInUser().get().getUserId();

        businessRepository.deleteAllByCreatedBy(userId);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("All user businesses has been successfully deleted")
                .build();
    }

    private BusinessResponse getBusinessById(UUID businessId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        User user = UserUtil.getLoggedInUser().get();

        Business business = businessRepository.findByBusinessId(businessId).orElseThrow(BusinessNotFoundException::new);

        if (!(business.getCreatedBy().equals(user.getUserId()) || business.getEmployees().parallelStream().map(User::getUserId).toList().contains(user.getUserId()))) {

            throw new UnauthorizedException("not an employee or owner of this business.");
        }

        return business.toDto();
    }

    public AppResponse addEmployees(UUID businessId, AddEmployeeRequest request) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        Business business = getBusinessByBusinessIdAndCreatedBy(businessId);

        List<User> employees = business.getEmployees();
        List<User> toBeAdded = userService.getUsersByListOfIds(request.getEmployeeIds().stream().toList());

        for (User user: toBeAdded) {

            if (employees.contains(user)){

                toBeAdded.remove(user);
            }
        }

        employees.addAll(toBeAdded);

        business.setEmployees(employees);

        businessRepository.save(business);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Employees have been successfully added").build();
    }

    public AppResponse removeEmployees(UUID businessId, AddEmployeeRequest request) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        Business business = getBusinessByBusinessIdAndCreatedBy(businessId);

        List<User> employees = business.getEmployees();
        List<User> toBeRemoved = userService.getUsersByListOfIds(request.getEmployeeIds().stream().toList());

        employees.removeAll(toBeRemoved);
        business.setEmployees(employees);

        businessRepository.save(business);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Employees have been successfully removed").build();
    }

    public Business getBusinessByBusinessIdAndCreatedBy(UUID businessId) {

        UUID userId = UserUtil.getLoggedInUser().get().getUserId();

        return businessRepository.findByBusinessIdAndCreatedBy(businessId, userId).orElseThrow(BusinessNotFoundException::new);
    }

    @Transactional
    public IdResponse createBusinessTool(UUID businessId, UUID toolId, BusinessToolRequest request) {

        BusinessTool businessTool = BusinessTool.builder()
                        .businessId(businessId)
                                .toolId(toolId)
                                        .name(request.getBusinessToolName().toUpperCase())
                                                .build();

        businessToolRepository.save(businessTool);

        List<BusinessToolValue> businessToolValues = new ArrayList<>();

        request.getToolValues().forEach(
                toolValue ->
                    businessToolValues.add(BusinessToolValue.builder()
                            .businessToolId(businessTool.getBusinessToolId())
                            .factorId(toolValue.getFactorId())
                            .value(toolValue.getValue())
                            .build())
                );

        businessTool.setBusinessToolValues(businessToolValues);

        businessToolRepository.save(businessTool);

        return IdResponse.builder()
                .id(businessTool.getBusinessToolId())
                .message("Business tool has been successfully created (Business Tool Id)").build();
    }
}
