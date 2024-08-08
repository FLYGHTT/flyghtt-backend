package com.flyghtt.flyghtt_backend.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("authentication/")
public class AuthenticationController {

    @GetMapping("sign-up")
    public String signUp() {

        return "Lmao";
    }
}
