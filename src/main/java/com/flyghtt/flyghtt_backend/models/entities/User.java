package com.flyghtt.flyghtt_backend.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Builder.Default
    private UUID userId = UUID.randomUUID();

    private String email;
    private String firstName;
    private String lastName;
    private String password;

    @Builder.Default private boolean emailVerified = false;
    @Builder.Default private boolean enabled = true;
    @Builder.Default private Instant createdAt = Instant.now();

    @Enumerated(EnumType.STRING)
    private Role role;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "userId")
    )
    private List<User> followers;

    @Override
    public boolean equals(Object user) {

        if (getClass() != user.getClass())
            return false;

        User user2 = (User) user;

        return userId.equals(user2.getUserId());
    }
}
