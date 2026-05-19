package com.lars.portfolio_manager.controllers;

import com.lars.portfolio_manager.entities.CustomUser;
import com.lars.portfolio_manager.services.PortfolioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final PortfolioService portfolioService;

    public PageController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal CustomUser currentUser,
                       Model model) {
        if (currentUser != null) {
            model.addAttribute("portfolios", portfolioService.findAllByOwner(currentUser));
        }
        return "home";
    }

}
