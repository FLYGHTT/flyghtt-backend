package com.flyghtt.flyghtt_backend.controllers;


import com.flyghtt.flyghtt_backend.models.requests.RegisterRequest;
import com.flyghtt.flyghtt_backend.models.response.AuthenticationResponse;
import com.flyghtt.flyghtt_backend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
}
