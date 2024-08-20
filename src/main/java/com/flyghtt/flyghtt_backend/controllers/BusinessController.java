package com.flyghtt.flyghtt_backend.controllers;

import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.requests.AddEmployeeRequest;
import com.flyghtt.flyghtt_backend.models.requests.BusinessRequest;
import com.flyghtt.flyghtt_backend.models.requests.BusinessToolRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.BusinessResponse;
import com.flyghtt.flyghtt_backend.models.response.IdResponse;
import com.flyghtt.flyghtt_backend.services.BusinessService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("business")
@RestController
public class BusinessController {

    private final BusinessService businessService;

    @Operation(summary = "Create Business")
    @PostMapping
    public BusinessResponse createBusiness(@RequestBody BusinessRequest request) throws UserNotFoundException {

        return businessService.createBusiness(request);
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
    public BusinessResponse updateBusinessDetails(@PathVariable UUID businessId, @RequestBody BusinessRequest request) {

        return businessService.updateBusinessDetails(businessId, request);
    }

    @Operation(summary = "Delete business")
    @DeleteMapping("{businessId}")
    public AppResponse deleteBusiness(@PathVariable UUID businessId) {

        return businessService.deleteBusiness(businessId);
    }

    @Operation(summary = "Delete all user businesses")
    @DeleteMapping
    public AppResponse deleteAllUserBusinesses() {

        return businessService.deleteAllUserBusinesses();
    }

    @Operation(summary = "Add business employee")
    @PostMapping("{businessId}/employee")
    public AppResponse addEmployees(@PathVariable UUID businessId, @RequestBody AddEmployeeRequest request) {

        return businessService.addEmployees(businessId, request);
    }

    @Operation(summary = "Remove employee")
    @DeleteMapping("{businessId}/employee")
    public AppResponse removeEmployees(@PathVariable UUID businessId, @RequestBody AddEmployeeRequest request) {

        return businessService.removeEmployees(businessId, request);
    }

    @PostMapping("{businessId}/tool/{toolId}")
    public IdResponse createBusinessTool(@PathVariable UUID businessId, @PathVariable UUID toolId, @RequestBody BusinessToolRequest request) {

        return businessService.createBusinessTool(businessId, toolId, request);
    }
}
