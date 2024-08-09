package com.flyghtt.flyghtt_backend.controllers;


import com.flyghtt.flyghtt_backend.exceptions.FlyghttException;
import com.flyghtt.flyghtt_backend.exceptions.OtpException;
import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.requests.LoginRequest;
import com.flyghtt.flyghtt_backend.models.requests.OtpRequest;
import com.flyghtt.flyghtt_backend.models.requests.PasswordResetRequest;
import com.flyghtt.flyghtt_backend.models.requests.RegisterRequest;
import com.flyghtt.flyghtt_backend.models.response.AppResponse;
import com.flyghtt.flyghtt_backend.models.response.AuthenticationResponse;
import com.flyghtt.flyghtt_backend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
    public AuthenticationResponse verifyOtp(@RequestBody OtpRequest request, @RequestHeader(name="Authorization") String token) throws UserNotFoundException, OtpException {

        return authenticationService.verifyOtp(request, token);
    }

    @GetMapping("reset/password/otp")
    public AuthenticationResponse sendOtpForResetPassword() throws UserNotFoundException {

        return authenticationService.sendOtpForResetPassword();
    }

    @PostMapping("reset/password")
    public AppResponse resetPassword(@RequestBody PasswordResetRequest request, @RequestHeader(name="Authorization") String token) throws FlyghttException {

        return authenticationService.resetPassword(request, token);
    }

    @PostMapping("change/password")
    public AppResponse changePassword(@RequestBody PasswordResetRequest request) throws FlyghttException {

        return authenticationService.changePassword(request);
    }
}
