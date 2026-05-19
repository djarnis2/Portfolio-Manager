package com.lars.portfolio_manager.controllers;

import com.lars.portfolio_manager.dto.TransactionForm;
import com.lars.portfolio_manager.entities.CustomUser;
import com.lars.portfolio_manager.entities.Exchange;
import com.lars.portfolio_manager.entities.Portfolio;
import com.lars.portfolio_manager.services.PortfolioService;
import com.lars.portfolio_manager.services.TickerInfoService;
import com.lars.portfolio_manager.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.aot.hint.annotation.RegisterReflection;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class TransactionController {

    TransactionService transactionService;
    PortfolioService portfolioService;
    TickerInfoService tickerInfoService;

    public TransactionController(PortfolioService portfolioService, TransactionService transactionService, TickerInfoService tickerInfoService) {
        this.portfolioService = portfolioService;
        this.transactionService = transactionService;
        this.tickerInfoService = tickerInfoService;
    }

//    @GetMapping("/api/exchange/tickers")
//    public TickerInfo getTickers(@RequestBody TickerInfo tickerInfo) {
//        return
//    }


    @GetMapping("/portfolio/{portfolioId}/transaction/new")
    public String newTransactionForm(@PathVariable Long portfolioId,
                                     @AuthenticationPrincipal CustomUser currentUser,
                                     @RequestParam(required = false) String code,
                                     @RequestParam(required = false) String isin,
                                     @RequestParam(required = false) String exchange,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) String currency,
                                     Model model) {
        Portfolio portfolio = portfolioService.findByIdAndOwner(portfolioId, currentUser);
        model.addAttribute("portfolio", portfolio);
        model.addAttribute("transactionForm", TransactionForm.empty());
        model.addAttribute("exchanges", Exchange.values());
        model.addAttribute("transactionForm", TransactionForm.fromTickerInfo(
                code,
                isin,
                exchange,
                name,
                currency
        ));

        return "transaction-form";
    }

    @GetMapping("/portfolio/{portfolioId}/transaction/new/search")
    public String searchTicker(@PathVariable Long portfolioId,
                                     @RequestParam Exchange exchange,
                                     @RequestParam String query,
                                     @AuthenticationPrincipal CustomUser currentUser,
                                     Model model) {

        Portfolio portfolio = portfolioService.findByIdAndOwner(portfolioId, currentUser);
        model.addAttribute("portfolio", portfolio);
        model.addAttribute("transactionForm", TransactionForm.empty());
        model.addAttribute("exchanges", Exchange.values());
        model.addAttribute("tickerInfo", tickerInfoService.tickerSearch(exchange, query));

        return "transaction-form";
    }

    @PostMapping("/portfolio/{portfolioId}/transactions")
    public String createTransaction(@PathVariable Long portfolioId,
                                    @Valid @ModelAttribute TransactionForm form,
                                    BindingResult bindingResult,
                                    @AuthenticationPrincipal CustomUser currentUser,
                                    Model model
                                    ) {
        Portfolio portfolio = portfolioService.findByIdAndOwner(portfolioId, currentUser);

        if ((form.code() == null || form.code().isBlank())
        && (form.isin() == null || form.isin().isBlank())) {
            bindingResult.rejectValue(
                    "code",
                    "transaction.identifier.required",
                    "Either Ticker or ISIN must be filled out"
            );
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute(portfolio);
            model.addAttribute(form);
            return "/transaction-form";
        }

        transactionService.createTransaction(portfolio, form);
        return "redirect:/portfolio/" + portfolioId;
    }

}
