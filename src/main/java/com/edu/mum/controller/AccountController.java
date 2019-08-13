package com.edu.mum.controller;

import com.edu.mum.domain.Account;
import com.edu.mum.domain.User;
import com.edu.mum.service.AccountService;
import com.edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AccountController {
    private AccountService accountService;
    private UserService userService;
    @Autowired
    public AccountController(AccountService accountService, UserService userService){
        this.accountService = accountService;
        this.userService = userService;
    }
    @PostMapping(value = {"/account/save/{id}"})
   public ModelAndView saveAccountInfo(@PathVariable("id") Long id, @Valid @ModelAttribute("account") Account account, BindingResult bindingResult,
                                 Model model){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.addObject("successMessage", "Account Details couldn't be saved");
            modelAndView.setViewName("views/posts/earningPostList");

        }
        else{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> user = this.userService.findByUsername(authentication.getName());
            account.setUser(user.get());
            accountService.saveAccount(account);

        }
        return new ModelAndView("redirect:/payment/save/" +id);
   }
}
