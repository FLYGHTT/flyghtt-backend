package com.flyghtt.flyghtt_backend.services;

import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.User;
import com.flyghtt.flyghtt_backend.models.response.ToolResponse;
import com.flyghtt.flyghtt_backend.models.response.UserResponse;
import com.flyghtt.flyghtt_backend.repositories.UserRepository;
import com.flyghtt.flyghtt_backend.services.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsersByListOfIds(List<UUID> userIds) {

        return userIds
                .parallelStream()
                .map(userId -> userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new))
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(UUID userId) {

        return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new).toDto();
    }

    public UserResponse getLoggedInUser() {

        return UserUtil.getLoggedInUser().get().toDto();
    }

    public List<ToolResponse> getUserLikedTools() {

        return UserUtil.getLoggedInUser().get().getLikedTools();
    }

    public List<ToolResponse> getUserFavouriteTools() {

        return UserUtil.getLoggedInUser().get().getFavouriteTools();
    }
}
