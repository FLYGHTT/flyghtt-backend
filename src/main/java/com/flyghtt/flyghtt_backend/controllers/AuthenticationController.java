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
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("authentication/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("sign-up")
    public AuthenticationResponse signUp(@RequestBody RegisterRequest request) {

        return authenticationService.signUp(request);
    }

    @PostMapping("login")
    public AuthenticationResponse login(@RequestBody LoginRequest request) throws UserNotFoundException {

        return authenticationService.login(request);
    }

    @PostMapping("verify/otp")
    public VerifyOtpResponse verifyOtp(@RequestBody OtpRequest request) throws UserNotFoundException, OtpException, OtpNotFoundException {

        return authenticationService.verifyOtp(request);
    }

    @GetMapping("send/otp/{email}")
    public AppResponse sendOtpToMail(@PathVariable String email) throws UserNotFoundException {

        return authenticationService.sendOtpToMailService(email);
    }

    @PostMapping("reset/password")
    public AppResponse resetPassword(@RequestBody PasswordResetRequest request) throws FlyghttException {

        return authenticationService.resetPassword(request);
    }

    @PostMapping("change/password")
    public AppResponse changePassword(@RequestBody ChangePasswordRequest request) throws FlyghttException {

        return authenticationService.changePassword(request);
    }
}
