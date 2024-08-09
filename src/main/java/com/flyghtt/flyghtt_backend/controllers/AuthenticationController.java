package com.flyghtt.flyghtt_backend.controllers;


import com.flyghtt.flyghtt_backend.exceptions.OtpException;
import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.requests.LoginRequest;
import com.flyghtt.flyghtt_backend.models.requests.OtpRequest;
import com.flyghtt.flyghtt_backend.models.requests.RegisterRequest;
import com.flyghtt.flyghtt_backend.models.response.AuthenticationResponse;
import com.flyghtt.flyghtt_backend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
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
}
