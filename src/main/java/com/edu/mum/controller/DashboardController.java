package com.edu.mum.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.edu.mum.domain.User;
import com.edu.mum.service.UserService;

@Controller
public class DashboardController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/dashboard")
	public String dashboard(Authentication authentication, Model model) {
		Optional<User> current_user = userService.findByUsername(authentication.getName());
		model.addAttribute("post_count", current_user.get().getPosts().size());
		return "views/home/dashboard";
	}
}
