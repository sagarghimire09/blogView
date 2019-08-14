package com.edu.mum.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.edu.mum.domain.User;
import com.edu.mum.service.PaymentService;
import com.edu.mum.service.PostService;
import com.edu.mum.service.UserService;

@Controller
public class DashboardController {
	@Autowired
	private UserService userService;
	@Autowired
	PostService postService;
	@Autowired
	PaymentService paymentService;
	
	@GetMapping("/dashboard")
	public String dashboard(Authentication authentication, Model model) {
		Optional<User> current_user = userService.findByUsername(authentication.getName());
		
		model.addAttribute("your_blogs_published", postService.getPostCountForUser(current_user.get(), true));
		model.addAttribute("your_blogs_unpublished", postService.getPostCountForUser(current_user.get(), false));
		
		model.addAttribute("total_blogs_published", postService.getTotalPostCount(true));
		model.addAttribute("total_blogs_unpublished", postService.getTotalPostCount(false));
		
		model.addAttribute("rewards_count", paymentService.getPaidPostCount());
		model.addAttribute("reward_amount", paymentService.getTotalPaidAmount());
		
		model.addAttribute("rewarded_userpost_count", paymentService.getPaidPostCountForUser(current_user.get()));
		model.addAttribute("rewarded_user_amount", paymentService.getPaidAmountForUser(current_user.get()));
		
		model.addAttribute("total_claims", postService.getClaimedPostCount());
		
		model.addAttribute("total_claim_byuser", postService.getClaimedPostCountByUser(current_user.get()));
		return "views/home/dashboard";
	}
}
