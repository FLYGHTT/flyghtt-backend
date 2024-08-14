package com.flyghtt.flyghtt_backend.services.utils;

import com.flyghtt.flyghtt_backend.exceptions.UnauthorizedException;
import com.flyghtt.flyghtt_backend.exceptions.UserNotFoundException;
import com.flyghtt.flyghtt_backend.models.entities.User;
import com.flyghtt.flyghtt_backend.models.entities.UserDetailsImpl;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@UtilityClass
public class UserUtil {

    public static Optional<User> getLoggedInUser() throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new UserNotFoundException();
        }
         UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());

        return Optional.of (User.builder()
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .email(userDetails.getEmail())
                .emailVerified(userDetails.isEmailVerified())
                .enabled(userDetails.isEnabled())
                .password(userDetails.getPassword())
                .userId(userDetails.getUserId())
                .role(userDetails.getRole())
                .followers(userDetails.getFollowers())
                .build());
    }

    public boolean hasRole(String roleName) throws UserNotFoundException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new UserNotFoundException();
        }

        // Check if the role with roleName exists in the authorities
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals(roleName)) {
                return true;
            }
        }

        return false;  // The role was not found in the authorities
    }

    public void throwErrorIfNotUserEmailVerifiedAndEnabled() {

        User user = UserUtil.getLoggedInUser().get();

        if (!user.isEmailVerified() || !user.isEnabled()) {

            throw new UnauthorizedException("Account is not valid (locked or email not verified)");
        }
    }
}
