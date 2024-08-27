package com.flyghtt.flyghtt_backend.controllers;


import com.flyghtt.flyghtt_backend.exceptions.FlyghttException;
import com.flyghtt.flyghtt_backend.exceptions.OtpException;
import com.flyghtt.flyghtt_backend.exceptions.OtpNotFoundException;
import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.requests.ChangePasswordRequest;
import com.flyghtt.flyghtt_backend.models.requests.LoginRequest;
import com.flyghtt.flyghtt_backend.models.requests.OtpRequest;
import com.flyghtt.flyghtt_backend.models.requests.PasswordResetRequest;
import com.flyghtt.flyghtt_backend.models.requests.RegisterRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.AuthenticationResponse;
import com.flyghtt.flyghtt_backend.models.response.VerifyOtpResponse;
import com.flyghtt.flyghtt_backend.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("authentication/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "User Signup", description = "sends otp to mail")
    @PostMapping("sign-up")
    public AuthenticationResponse signUp(@RequestBody RegisterRequest request) {

        return authenticationService.signUp(request);
    }

    @Operation(summary = "User Login", description = "Sends an otp to mail if email verified is not true (NOTE) you cannot access other endpoints if you're not email verified")
    @PostMapping("login")
    public AuthenticationResponse login(@RequestBody LoginRequest request) throws UserNotFoundException {

        return authenticationService.login(request);
    }

    @Operation(summary = "Verify Otp", description = "endpoint is protected so make sure to pass token into the request")
    @PostMapping("verify/otp")
    public VerifyOtpResponse verifyOtp(@RequestBody OtpRequest request) throws UserNotFoundException, OtpException, OtpNotFoundException {

        return authenticationService.verifyOtp(request);
    }

    @Operation(summary = "Send Otp to email", description = "endpoint for sending an otp to mail (not protected) like when someone wants to reset password or something")
    @GetMapping("send/otp/{email}")
    public AppResponse sendOtpToMail(@PathVariable String email) throws UserNotFoundException {

        return authenticationService.sendOtpToMailService(email);
    }

    @Operation(summary = "Reset Password", description = "endpoint is not protected")
    @PostMapping("reset/password")
    public AppResponse resetPassword(@RequestBody PasswordResetRequest request) throws FlyghttException {

        return authenticationService.resetPassword(request);
    }

    @Operation(summary = "Change Password", description = "endpoint is protected")
    @PostMapping("change/password")
    public AppResponse changePassword(@RequestBody ChangePasswordRequest request) throws FlyghttException {

        return authenticationService.changePassword(request);
    }

    @GetMapping
    public Map<String, String> something() {

        return Map.of("message", "Idk man");
    }
}
