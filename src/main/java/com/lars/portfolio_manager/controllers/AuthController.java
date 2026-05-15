package com.lars.portfolio_manager.controllers;

import com.lars.portfolio_manager.dto.RegisterForm;
import com.lars.portfolio_manager.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm("","","",""));
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm  form,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (!form.password().equals(form.matchingPassword())) {
            bindingResult.rejectValue(
                    "matchingPassword",
                    "password.mismatch",
                    "Passwords do not match"
            );
        }

        if (userService.usernameExists(form.username())) {
            bindingResult.rejectValue(
                    "username",
                    "username.exists",
                    "Username is already taken"
            );
        }

        if (userService.emailExists(form.email())) {
            bindingResult.rejectValue(
                    "email",
                    "email.exists",
                    "Email is already registered"
            );
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.register(form);

        redirectAttributes.addFlashAttribute("registrationSuccess", true);

        return "redirect:/login";
    }

}
