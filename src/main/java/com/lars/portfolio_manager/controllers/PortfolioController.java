package com.lars.portfolio_manager.controllers;

import com.lars.portfolio_manager.dto.ValuatedStockHolding;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class PortfolioController {

    PortfolioService portfolioService;
    TransactionService transactionService;
    double usdToDkk = 6.45;


    public PortfolioController(PortfolioService portfolioService, TransactionService transactionService) {
        this.portfolioService = portfolioService;
        this.transactionService = transactionService;
    }

    @GetMapping("/portfolio/new")
    public String newPortfolioForm(Model model) {
        model.addAttribute("portfolioForm", new PortfolioForm(""));
        return "portfolio-form";
    }

    @GetMapping("/portfolio-transactions/{id}")
    public String showPortfolioTransactions(@PathVariable Long id,
                                @AuthenticationPrincipal CustomUser currentUser,
                                Model model) {
        Portfolio portfolio = portfolioService.findByIdAndOwner(id, currentUser);

        model.addAttribute("portfolio", portfolio);
        model.addAttribute("transactions", transactionService.findTransactionsForPortfolio(portfolio));
        model.addAttribute("cashValue", portfolioService.getCashValue(portfolio, usdToDkk));
        model.addAttribute("stockValue", portfolioService.getTransactionValue(portfolio, usdToDkk));
        return "transaction-detail";
    }

    @GetMapping("/portfolio-detail/{id}")
    public String showPortfolioOverview(@PathVariable Long id,
                                @AuthenticationPrincipal CustomUser currentUser,
                                Model model) {
        Portfolio portfolio = portfolioService.findByIdAndOwner(id, currentUser);

        model.addAttribute("portfolio", portfolio);
        model.addAttribute("overview", portfolioService.getValuatedHoldingOverview(portfolio));
        model.addAttribute("cashValue", portfolioService.getCashValue(portfolio, usdToDkk));
        model.addAttribute("stockPurchasePrice", portfolioService.getTransactionValue(portfolio, usdToDkk));
        model.addAttribute("currentValue", portfolioService.getCurrentHoldingsValueInKroner(portfolio, usdToDkk));

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

        return "redirect:/portfolio-transactions/" + portfolio.getId();
    }

    @PostMapping("/portfolio-detail/{id}/update-prices")
    public String updateStockPrices(@PathVariable Long id,
                                    @AuthenticationPrincipal CustomUser currentUser,
                                    RedirectAttributes redirectAttributes
                                    ) {
        Portfolio portfolio = portfolioService.findByIdAndOwner(id,currentUser);

        try {
            int updated = portfolioService.updateLatestPrices(portfolio);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Updated prices for " + updated + " intruments"
            );
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Could not update prices. Maybe API exceeded number of free calls"
            );
        }

        return "redirect:/portfolio-detail/" + id;
    }

}
