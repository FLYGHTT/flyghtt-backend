package com.flyghtt.flyghtt_backend.controllers;

import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.BusinessTool;
import com.flyghtt.flyghtt_backend.models.requests.AddCollaboratorRequest;
import com.flyghtt.flyghtt_backend.models.requests.BusinessRequest;
import com.flyghtt.flyghtt_backend.models.requests.BusinessToolRequest;
import com.flyghtt.flyghtt_backend.models.requests.CollaboratorIdRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.BusinessLogoResponse;
import com.flyghtt.flyghtt_backend.models.response.BusinessResponse;
import com.flyghtt.flyghtt_backend.models.response.IdResponse;
import com.flyghtt.flyghtt_backend.repositories.ApprovalStatusRequest;
import com.flyghtt.flyghtt_backend.services.BusinessService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("business")
@RestController
public class BusinessController {

    private final BusinessService businessService;

    @Operation(summary = "Create Business")
    @PostMapping
    public BusinessResponse createBusiness(@ModelAttribute BusinessRequest request) throws UserNotFoundException, IOException {

        return businessService.createBusiness(request);
    }

    @GetMapping("/{businessId}/logo")
    public BusinessLogoResponse getBusinessLogo(@PathVariable UUID businessId) {

        return businessService.getBusinessLogo(businessId);
    }

    @Operation(summary = "Get business by business Id")
    @GetMapping("{businessId}")
    public BusinessResponse getBusiness(@PathVariable UUID businessId) {

        return businessService.getByBusinessId(businessId);
    }

    @Operation(summary = "Ger all user businesses")
    @GetMapping("user")
    public List<BusinessResponse> getAllUserBusinesses() {

        return businessService.getAllUserBusinesses();
    }

    @Operation(summary = "Update business details")
    @PutMapping("{businessId}")
    public BusinessResponse updateBusinessDetails(@PathVariable UUID businessId, @ModelAttribute BusinessRequest request) throws IOException {

        return businessService.updateBusinessDetails(businessId, request);
    }

    @Operation(summary = "Delete business")
    @DeleteMapping("{businessId}")
    public AppResponse deleteBusiness(@PathVariable UUID businessId) {

        return businessService.deleteBusiness(businessId);
    }

    @Operation(summary = "Add business collaborator")
    @PostMapping("{businessId}/collaborator")
    public AppResponse requestCollaborator(@PathVariable UUID businessId, @RequestBody Set<AddCollaboratorRequest> request) {

        return businessService.requestCollaborator(businessId, request);
    }

    @Operation(summary = "Remove collaborator")
    @DeleteMapping("{businessId}/collaborator")
    public AppResponse removeCollaborator(@PathVariable UUID businessId, @RequestBody CollaboratorIdRequest request) {

        return businessService.removeCollaborator(businessId, request);
    }

    @Operation(summary = "User joining business after request has been sent")
    @PostMapping("{businessId}/join")
    public AppResponse joinBusiness(@PathVariable UUID businessId, @RequestBody ApprovalStatusRequest status) {

        return businessService.joinBusiness(businessId, status.getApprovalStatus());
    }

    @Operation(summary = "User leaving business")
    @DeleteMapping("{businessId}/leave")
    public AppResponse leaveBusiness(@PathVariable UUID businessId) {

        return businessService.leaveBusiness(businessId);
    }

    @Operation(summary = "Adding values to tool for a business")
    @PostMapping("{businessId}/tool/{toolId}")
    public IdResponse createBusinessTool(@PathVariable UUID businessId, @PathVariable UUID toolId, @RequestBody BusinessToolRequest request) {

        return businessService.createBusinessTool(businessId, toolId, request);
    }

    @Operation(summary = "Getting all business tools with values in them by business id")
    @GetMapping("{businessId}/tools")
    public List<BusinessTool> getAllBusinessToolsByBusinessId(@PathVariable UUID businessId) {

        return businessService.getBusinessToolsByBusinessId(businessId);
    }
}
