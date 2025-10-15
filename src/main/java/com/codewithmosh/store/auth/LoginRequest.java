package com.codewithmosh.store.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email field is empty.")
    private String email;
    @NotBlank(message = "Password field is empty.")
    private String password;
}
