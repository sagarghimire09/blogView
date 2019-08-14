package com.edu.mum.controller;

import com.edu.mum.domain.User;
import com.edu.mum.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsersController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * Get a list of all posts in the database, it should be able to paginate and sort
	 * http://localhost:8090/posts?page=0&size=3&sort=id
	 *  
	 * @param model
	 * @return
	 */
	@RequestMapping("/users")
	public String index(Model model, @PageableDefault(sort = {"username"}, value = 5) Pageable pageable){
		// Get the content of the table, TODO. find a way to paginate
		Page<User> users = this.userService.findAll(pageable);
		
		// Define variables to be passed to view
		model.addAttribute("users", users);
		// Return the view model itself
		return "views/users/userList";
	}
	
	@GetMapping("/profile")
	public String getUser(Authentication authentication, Model model) {
		Optional<User> current_user = userService.findByUsername(authentication.getName());
//		System.out.println(current_user.toString());
		model.addAttribute("current_user", current_user.get());
		return "views/users/profile";
	}

}
