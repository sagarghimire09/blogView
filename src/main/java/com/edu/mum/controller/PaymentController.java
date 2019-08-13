package com.edu.mum.controller;

import com.edu.mum.domain.Account;
import com.edu.mum.domain.Payment;
import com.edu.mum.domain.Post;
import com.edu.mum.domain.User;
import com.edu.mum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class PaymentController {

    private PaymentService paymentService;
    private UserService userService;
    private AccountService accountService;
    private PostService postService;
    private NotificationService notificationService;
    @Autowired
    public PaymentController(PaymentService paymentService, UserService userService,
                             AccountService accountService, PostService postService, NotificationService notificationService){
        this.paymentService = paymentService;
        this.userService = userService;
        this.accountService = accountService;
        this.postService = postService;
        this.notificationService = notificationService;
    }

    @GetMapping(value = {"/payment/save/{id}"})
    public String savePayment(@PathVariable("id") Long id, Model model){

        ModelAndView modelAndView = new ModelAndView();
        Payment payment = new Payment();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> user = this.userService.findByUsername(authentication.getName());
            payment.setUser(user.get());
            payment.setAccount(accountService.getAccountByUser(user.get().getId()));
            payment.setPost(postService.findById(id).get());
            payment.setStatus(false);
            payment.setPayAmount(postService.findById(id).get().getEarning());
            if(paymentService.savePayment(payment)) {
                postService.findById(id).get().setClaimedStatus(true);
                postService.create(postService.findById(id).get());
            }
        return "redirect:/posts/earning";
    }



    @RequestMapping(value = {"/payment/issue/{id}"})
    public String sendPayment(@PathVariable("id") Long id, Model model){
        Payment payment = paymentService.findPaymentByPostId(id);
        if(payment != null){
            payment.setStatus(true);
            paymentService.savePayment(payment);
        }
        else{
            notificationService.addErrorMessage("Cannot make payment for post #" + id);
            return "redirect:/error";
        }
        return "redirect:/posts/earning";
    }
}
