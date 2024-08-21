package com.flyghtt.flyghtt_backend.controllers;

import com.flyghtt.flyghtt_backend.models.response.ToolResponse;
import com.flyghtt.flyghtt_backend.models.response.UserResponse;
import com.flyghtt.flyghtt_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("{userId}")
    public UserResponse getUserById(@PathVariable UUID userId) {

        return userService.getUserById(userId);
    }

    @GetMapping
    public UserResponse getLoggedInUser() {

        return userService.getLoggedInUser();
    }

    @GetMapping("tools/liked")
    public List<ToolResponse> getLikedTools() {

        return userService.getUserLikedTools();
    }

    @GetMapping("tools/favourite")
    public List<ToolResponse> getFavouriteTools() {

        return userService.getUserFavouriteTools();
    }
}
