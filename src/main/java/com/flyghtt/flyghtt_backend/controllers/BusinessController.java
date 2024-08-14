package com.flyghtt.flyghtt_backend.controllers;

import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.requests.AddEmployeeRequest;
import com.flyghtt.flyghtt_backend.models.requests.BusinessRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.BusinessResponse;
import com.flyghtt.flyghtt_backend.services.BusinessService;
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

    @PostMapping
    public BusinessResponse createBusiness(@RequestBody BusinessRequest request) throws UserNotFoundException {

        return businessService.createBusiness(request);
    }

    @GetMapping("{businessId}")
    public BusinessResponse getBusiness(@PathVariable UUID businessId) {

        return businessService.getByBusinessId(businessId);
    }

    @GetMapping("user")
    public List<BusinessResponse> getAllUserBusinesses() {

        return businessService.getAllUserBusinesses();
    }

    @PutMapping("{businessId}")
    public BusinessResponse updateBusinessDetails(@PathVariable UUID businessId, @RequestBody BusinessRequest request) {

        return businessService.updateBusinessDetails(businessId, request);
    }

    @DeleteMapping("{businessId}")
    public AppResponse deleteBusiness(@PathVariable UUID businessId) {

        return businessService.deleteBusiness(businessId);
    }

    @DeleteMapping
    public AppResponse deleteAllUserBusinesses() {

        return businessService.deleteAllUserBusinesses();
    }

    @PostMapping("{businessId}/employee")
    public AppResponse addEmployees(@PathVariable UUID businessId, @RequestBody AddEmployeeRequest request) {

        return businessService.addEmployees(businessId, request);
    }

    @DeleteMapping("{businessId}/employee")
    public AppResponse removeEmployees(@PathVariable UUID businessId, @RequestBody AddEmployeeRequest request) {

        return businessService.removeEmployees(businessId, request);
    }
}
