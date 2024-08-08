package com.flyghtt.flyghtt_backend.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Data
@Builder
public class UserDetailsImpl implements UserDetails {

    private UUID userId;
    private Collection<GrantedAuthority> authorities;
    private String password;
    private String email;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String username;

    public String getUsername() {

        return email;
    }

    public static UserDetailsImpl build(User user) {

        return new UserDetailsImpl(
                user.getUserId(),
                List.of(new SimpleGrantedAuthority(user.getRole().toString())),
                user.getPassword(),
                user.getEmail(),
                true,
                user.isEnabled(),
                true,
                user.isEnabled(),
                user.getEmail() // email is being used as the username
        );
    }
}
