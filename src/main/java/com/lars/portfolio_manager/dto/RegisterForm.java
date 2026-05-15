package com.lars.portfolio_manager.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record RegisterForm(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "email is required")
        String email,
        @NotBlank(message = "password is required")
        String password
) {}