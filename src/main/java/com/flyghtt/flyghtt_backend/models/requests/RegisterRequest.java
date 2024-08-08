package com.flyghtt.flyghtt_backend.models.requests;


import com.flyghtt.flyghtt_backend.models.entities.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {

    private String firstName;
    private String lastName;
    @NotNull private String password;
    @NotNull private String email;
    private Role role;
}
