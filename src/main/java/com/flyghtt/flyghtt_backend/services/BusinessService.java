package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.exceptions.BusinessCollaboratorRequestNotFoundException;
import com.flyghtt.flyghtt_backend.exceptions.BusinessNotFoundException;
import com.flyghtt.flyghtt_backend.exceptions.UnauthorizedException;
import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.ApprovalStatus;
import com.flyghtt.flyghtt_backend.models.entities.Business;
import com.flyghtt.flyghtt_backend.models.entities.BusinessCollaborator;
import com.flyghtt.flyghtt_backend.models.entities.BusinessCollaboratorRequest;
import com.flyghtt.flyghtt_backend.models.entities.BusinessTool;
import com.flyghtt.flyghtt_backend.models.entities.User;
import com.flyghtt.flyghtt_backend.models.requests.AddCollaboratorRequest;
import com.flyghtt.flyghtt_backend.models.requests.BusinessRequest;
import com.flyghtt.flyghtt_backend.models.requests.BusinessToolRequest;
import com.flyghtt.flyghtt_backend.models.requests.CollaboratorIdRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.BusinessLogoResponse;
import com.flyghtt.flyghtt_backend.models.response.BusinessResponse;
import com.flyghtt.flyghtt_backend.models.response.IdResponse;
import com.flyghtt.flyghtt_backend.repositories.BusinessCollaboratorRepository;
import com.flyghtt.flyghtt_backend.repositories.BusinessCollaboratorRequestRepository;
import com.flyghtt.flyghtt_backend.repositories.BusinessRepository;
import com.flyghtt.flyghtt_backend.repositories.BusinessToolRepository;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final BusinessCollaboratorRepository businessCollaboratorRepository;
    private final BusinessToolRepository businessToolRepository;
    private final BusinessToolService businessToolService;
    private final BusinessLogoService businessLogoService;
    private final BusinessCollaboratorRequestRepository businessCollaboratorRequestRepository;

    @Transactional
    public BusinessResponse createBusiness(BusinessRequest request) throws UserNotFoundException, IOException {

        request.clean();

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        User user = UserUtil.getLoggedInUser().get();

        Business business = Business.builder()
                .name(request.getBusinessName().toUpperCase())
                .description(request.getDescription())
                .createdBy(user.getUserId())
                .createdAt(Instant.now())
                .collaborators(new HashSet<>())
                .businessTools(new ArrayList<>())
                .build();

        saveBusiness(business);

        if (request.getBusinessLogo() == null) {

            return business.toDto(null);
        }

        return business.toDto(businessLogoService.uploadImage(request.getBusinessLogo(), business.getBusinessId()));
    }

    public BusinessResponse getByBusinessId(UUID businessId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        return getBusinessById(businessId);
    }

    public List<BusinessResponse> getAllUserBusinesses() {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        UUID userId = UserUtil.getLoggedInUser().get().getUserId();

        return businessRepository.findAllByCreatedByOrderByCreatedAtDesc(userId).parallelStream().map(
                    business -> business.toDto(businessLogoService.downloadImage(business.getBusinessId()))
                )
                .collect(Collectors.toList());
    }

    @Transactional
    public BusinessResponse updateBusinessDetails(UUID businessId, BusinessRequest request) throws IOException {

        request.clean();

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        UUID userId = UserUtil.getLoggedInUser().get().getUserId();
        Business business = businessRepository.findByBusinessIdAndCreatedBy(businessId, userId).orElseThrow(BusinessNotFoundException::new);

        business.setName(request.getBusinessName().toUpperCase());
        business.setDescription(request.getDescription());

        if (request.getBusinessLogo() == null) {

            businessLogoService.deleteByBusinessId(businessId);
            return business.toDto(null);
        }

        return saveBusiness(business).toDto(businessLogoService.updateBusinessLogo(businessId, request.getBusinessLogo()));
    }

    @Transactional
    public AppResponse deleteBusiness(UUID businessId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        UUID userId = UserUtil.getLoggedInUser().get().getUserId();

        deleteBusinessCollaborators(businessId);
        businessLogoService.deleteByBusinessId(businessId);
        businessToolService.deleteBusinessToolsByBusinessId(businessId);
        businessRepository.deleteByBusinessIdAndCreatedBy(businessId, userId);

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Business has been successfully deleted")
                .build();
    }

    private void deleteBusinessCollaborators(UUID businessId) {

        businessCollaboratorRepository.deleteAllByBusinessId(businessId);
        businessCollaboratorRequestRepository.deleteAllByBusinessId(businessId);
    }

    private BusinessResponse getBusinessById(UUID businessId) {

        UserUtil.throwErrorIfNotUserEmailVerifiedAndEnabled();

        User user = UserUtil.getLoggedInUser().get();

        Business business = businessRepository.findByBusinessId(businessId).orElseThrow(BusinessNotFoundException::new);

        if (!(business.getCreatedBy().equals(user.getUserId()) || business.getCollaborators().parallelStream().map(BusinessCollaborator::getUserId).toList().contains(user.getUserId()))) {

            throw new UnauthorizedException("not an employee or owner of this business.");
        }

        return business.toDto(businessLogoService.downloadImage(businessId));
    }

    public Business getBusinessByBusinessIdAndCreatedBy(UUID businessId) {

        UUID userId = UserUtil.getLoggedInUser().get().getUserId();

        return businessRepository.findByBusinessIdAndCreatedBy(businessId, userId).orElseThrow(BusinessNotFoundException::new);
    }

    @Transactional
    public IdResponse createBusinessTool(UUID businessId, UUID toolId, BusinessToolRequest request) {

//        BusinessTool businessTool = BusinessTool.builder()
//                        .businessId(businessId)
//                                .toolId(toolId)
//                                        .name(request.getBusinessToolName().toUpperCase())
//                                                .build();
//
//        businessToolRepository.save(businessTool);
//
//        List<BusinessToolValue> businessToolValues = new ArrayList<>();
//
//        request.getToolValues().forEach(
//                toolValue ->
//                    businessToolValues.add(BusinessToolValue.builder()
//                            .businessToolId(businessTool.getBusinessToolId())
//                            .factorId(toolValue.getFactorId())
//                            .value(toolValue.getValue())
//                            .build())
//                );
//
//        businessTool.setBusinessToolValues(businessToolValues);
//
//        businessToolRepository.save(businessTool);
//
//        return IdResponse.builder()
//                .id(businessTool.getBusinessToolId())
//                .message("Business tool has been successfully created (Business Tool Id)").build();

        return null;
    }

    public List<BusinessTool> getBusinessToolsByBusinessId(UUID businessId) {

        return businessToolRepository.findAllByBusinessIdOrderByCreatedAtDesc(businessId);
    }

    public BusinessLogoResponse getBusinessLogo(UUID businessId) {

        byte[] imageData = businessLogoService.downloadImage(businessId);
        return BusinessLogoResponse.builder()
                .imageData(imageData).build();
    }

    public Business saveBusiness(Business business) {

        try {
            Business toBeSaved = businessRepository.save(business);
            businessRepository.flush();

            return toBeSaved;

        } catch (DataIntegrityViolationException ex) {

            throw new com.flyghtt.flyghtt_backend.exceptions.DataIntegrityViolationException("BUSINESS NAME " + business.getName());
        }
    }

    public AppResponse requestCollaborator(UUID businessId, Set<AddCollaboratorRequest> requests) {

        throwErrorIfNotBusinessOwner(businessId);
        requests.forEach(
                request -> {

                    BusinessCollaboratorRequest collaboratorRequest = BusinessCollaboratorRequest.builder()
                            .businessId(businessId)
                            .collaboratorId(request.getCollaboratorId())
                            .role(request.getRole())
                            .approvalStatus(ApprovalStatus.PENDING).build();

                    businessCollaboratorRequestRepository.save(collaboratorRequest);
                }
        );

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Requests have been sent successfully").build();
    }

    @Transactional
    public AppResponse removeCollaborator(UUID businessId, CollaboratorIdRequest request) {

        throwErrorIfNotBusinessOwner(businessId);
        request.getCollaboratorIds().forEach(
                collaboratorId -> businessCollaboratorRepository.deleteByBusinessIdAndUserId(businessId, collaboratorId));

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Employees have been successfully removed").build();
    }

    @Transactional
    public AppResponse joinBusiness(UUID businessId, ApprovalStatus status) {

        User loggedInUser = UserUtil.getLoggedInUser().get();
        BusinessCollaboratorRequest businessCollaboratorRequest = businessCollaboratorRequestRepository.findByCollaboratorIdAndBusinessId(loggedInUser.getUserId(), businessId).orElseThrow(BusinessCollaboratorRequestNotFoundException::new);

        BusinessCollaborator businessCollaborator = BusinessCollaborator.builder()
                .role(businessCollaboratorRequest.getRole())
                .businessId(businessId)
                .userId(loggedInUser.getUserId()).build();

        businessCollaboratorRequestRepository.deleteById(businessCollaboratorRequest.getBusinessCollaboratorRequestId());


        String message = "You're successfully rejected invite to join " + businessRepository.findByBusinessId(businessId).get().getName();

        if (status.equals(ApprovalStatus.APPROVED)) {

            message = "You're successfully joined this " + businessRepository.findByBusinessId(businessId).get().getName();
            businessCollaboratorRepository.save(businessCollaborator);
        }

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message(message)
                .build();
    }

    @Transactional
    public AppResponse leaveBusiness(UUID businessId) {

        businessCollaboratorRepository.deleteByBusinessIdAndUserId(businessId, UserUtil.getLoggedInUser().get().getUserId());

        return AppResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully left business").build();
    }

    public void throwErrorIfNotBusinessOwner(UUID businessId) {

        if (!businessRepository.existsByBusinessIdAndCreatedBy(businessId, UserUtil.getLoggedInUser().get().getUserId())) {

            throw new UnauthorizedException("you're not the business owner");
        }
    }
}
