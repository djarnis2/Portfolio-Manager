package com.lars.portfolio_manager.dto;

public record RegisterForm(
        String username,
        String email,
        String password
) {}