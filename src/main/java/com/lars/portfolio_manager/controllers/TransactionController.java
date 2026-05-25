package com.lars.portfolio_manager.controllers;

import com.lars.portfolio_manager.dto.CashTransactionForm;
import com.lars.portfolio_manager.dto.TransactionForm;
import com.lars.portfolio_manager.entities.*;
import com.lars.portfolio_manager.services.InstrumentService;
import com.lars.portfolio_manager.services.PortfolioService;
import com.lars.portfolio_manager.services.TickerInfoService;
import com.lars.portfolio_manager.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TransactionController {

    TransactionService transactionService;
    PortfolioService portfolioService;
    TickerInfoService tickerInfoService;
    InstrumentService instrumentService;

    public TransactionController(
            PortfolioService portfolioService,
            TransactionService transactionService,
            TickerInfoService tickerInfoService,
            InstrumentService instrumentService) {
        this.portfolioService = portfolioService;
        this.transactionService = transactionService;
        this.tickerInfoService = tickerInfoService;
        this.instrumentService = instrumentService;
    }


    @GetMapping("/portfolio-transactions/{portfolioId}/transaction/new")
    public String newTransactionForm(@PathVariable Long portfolioId,
                                     @AuthenticationPrincipal CustomUser currentUser,
                                     @RequestParam(required = false) String code,
                                     @RequestParam(required = false) String isin,
                                     @RequestParam(required = false) String exchange,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) String currency,
                                     @RequestParam(required = false) String instrumentType,
                                     Model model) {
        Portfolio portfolio = portfolioService.findByIdAndOwner(portfolioId, currentUser);
        model.addAttribute("portfolio", portfolio);
        model.addAttribute("transactionForm", TransactionForm.empty());
        model.addAttribute("exchanges", Exchange.values());
        model.addAttribute("transactionForm", TransactionForm.fromTickerInfo(
                code,
                isin,
                name,
                exchange,
                currency,
                instrumentType)
        );

        return "transaction-form";
    }

    @GetMapping("/portfolio-transactions/{portfolioId}/transaction/cash")
    public String newCashTransactionForm(@PathVariable Long portfolioId,
                                     @AuthenticationPrincipal CustomUser currentUser,
                                     Model model) {
        Portfolio portfolio = portfolioService.findByIdAndOwner(portfolioId, currentUser);
        model.addAttribute("portfolio", portfolio);
        model.addAttribute("cashTransactionForm", CashTransactionForm.empty());
        model.addAttribute("transactionType", List.of(TransactionType.DEPOSIT, TransactionType.WITHDRAW));

        return "cash-transaction";
    }

    @GetMapping("/portfolio-transactions/{portfolioId}/transaction/new/search")
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

    @PostMapping("/portfolio-transactions/{portfolioId}/transactions")
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
            model.addAttribute("exchanges", Exchange.values());
            return "/transaction-form";
        }


        transactionService.createTransaction(portfolio, form);
        return "redirect:/portfolio-transactions/" + portfolioId;
    }

    @PostMapping("/portfolio-transactions/{portfolioId}/cashtransactions")
    public String createCashTransaction(@PathVariable Long portfolioId,
                                    @Valid @ModelAttribute CashTransactionForm form,
                                    BindingResult bindingResult,
                                    @AuthenticationPrincipal CustomUser currentUser,
                                    Model model
    ) {
        Portfolio portfolio = portfolioService.findByIdAndOwner(portfolioId, currentUser);

        if (bindingResult.hasErrors()) {
            model.addAttribute(portfolio);
            model.addAttribute(form);
            return "/cash-transaction";
        }

        transactionService.createCashTransaction(portfolio, form);
        return "redirect:/portfolio-transactions/" + portfolioId;
    }

}
