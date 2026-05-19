package com.lars.portfolio_manager.controllers;

import com.lars.portfolio_manager.entities.CustomUser;
import com.lars.portfolio_manager.dto.PortfolioForm;
import com.lars.portfolio_manager.entities.Portfolio;
import com.lars.portfolio_manager.services.PortfolioService;
import com.lars.portfolio_manager.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PortfolioController {

    PortfolioService portfolioService;
    TransactionService transactionService;


    public PortfolioController(PortfolioService portfolioService, TransactionService transactionService) {
        this.portfolioService = portfolioService;
        this.transactionService = transactionService;
    }

    @GetMapping("/portfolio/new")
    public String newPortfolioForm(Model model) {
        model.addAttribute("portfolioForm", new PortfolioForm(""));
        return "portfolio-form";
    }

    @GetMapping("/portfolio/{id}")
    public String showPortfolio(@PathVariable Long id,
                                @AuthenticationPrincipal CustomUser currentUser,
                                Model model) {
        Portfolio portfolio = portfolioService.findByIdAndOwner(id, currentUser);

        model.addAttribute("portfolio", portfolio);
        model.addAttribute("transcations", transactionService.findTransactionsForPortfolio(portfolio));
        return "portfolio-detail";
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

        Portfolio portfolio = portfolioService.createPortfolio(form.name(), currentUser);

        return "redirect:/portfolio/" + portfolio.getId();
    }

}
