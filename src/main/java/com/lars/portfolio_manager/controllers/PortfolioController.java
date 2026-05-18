package com.lars.portfolio_manager.controllers;

import com.lars.portfolio_manager.entities.CustomUser;
import com.lars.portfolio_manager.dto.PortfolioForm;
import com.lars.portfolio_manager.services.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PortfolioController {

    PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/portfolio/new")
    public String newPortfolioForm(Model model) {
        model.addAttribute("portfolioForm", new PortfolioForm(""));
        return "portfolio-form";
    }

    @PostMapping("/portfolios")
    public String createPortfolio(@Valid @ModelAttribute PortfolioForm form,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal CustomUser currentUser) {
        if (currentUser == null) {
            return "/login";
        }



        if (portfolioService.portfolioNameExistsForUser(currentUser, form.name())) {
            bindingResult.rejectValue(
                    "name",
                    "portfolio.name.exists",
                    "You already have a portfolio with this name"
            );
        }

        if (bindingResult.hasErrors()) {
            return "portfolio-form";
        }

        portfolioService.createPortfolio(form.name(), currentUser);

        return "redirect:/";
    }

}
