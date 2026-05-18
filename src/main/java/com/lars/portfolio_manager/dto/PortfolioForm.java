package com.lars.portfolio_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PortfolioForm(

        @NotBlank(message = "Portfolio name is required")
        @Size(max = 50, message = "Portfolio name can be max 50 characters long")
        String name
) {

}
