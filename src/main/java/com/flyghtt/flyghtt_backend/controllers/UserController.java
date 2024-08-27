package com.flyghtt.flyghtt_backend.controllers;

import com.flyghtt.flyghtt_backend.models.response.ToolResponse;
import com.flyghtt.flyghtt_backend.models.response.UserResponse;
import com.flyghtt.flyghtt_backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "get user by id")
    @GetMapping("{userId}")
    public UserResponse getUserById(@PathVariable UUID userId) {

        return userService.getUserById(userId);
    }

    @Operation(summary = "get logged in user")
    @GetMapping
    public UserResponse getLoggedInUser() {

        return userService.getLoggedInUser();
    }

    @Operation(summary = "get all user liked tools")
    @GetMapping("tools/liked")
    public List<ToolResponse> getLikedTools() {

        return userService.getUserLikedTools();
    }

    @Operation(summary = "get all user favourite tools")
    @GetMapping("tools/favourite")
    public List<ToolResponse> getFavouriteTools() {

        return userService.getUserFavouriteTools();
    }
}
