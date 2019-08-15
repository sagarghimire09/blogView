package com.edu.mum.controller;

import com.edu.mum.domain.Post;
import com.edu.mum.domain.User;
import com.edu.mum.repository.UserRepository;
import com.edu.mum.service.CommentService;
import com.edu.mum.service.NotificationService;
import com.edu.mum.service.PostService;
import com.edu.mum.service.ReviewService;
import com.edu.mum.service.UserService;

import java.util.Optional;

import javax.validation.Valid;

import com.edu.mum.util.ArithmeticUtils;
import com.edu.mum.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsersController {
	
	@Autowired
	private UserService userService;
	@Autowired
	NotificationService notifyService;
	@Autowired
	CommentService commentService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	PostService postService;
	
	/**
	 * Get a list of all posts in the database, it should be able to paginate and sort
	 * http://localhost:8090/posts?page=0&size=3&sort=id
	 *  
	 * @param model
	 * @return
	 */
	@RequestMapping("/users")
	public String index(@RequestParam( defaultValue = "0") int page,Model model){
		Page<User> posts = this.userService.findAllByOrderByFirstName(page);
		Pager pager = new Pager(posts);
		model.addAttribute("pager", pager);
		return "views/users/userList";

	}
	
	@GetMapping("/profile")
	public String getUser(Authentication authentication, Model model) {
		Optional<User> current_user = userService.findByUsername(authentication.getName());
		model.addAttribute("current_user", current_user.get());
		model.addAttribute("comments", commentService.getCommentCountForUser(current_user.get()));
		model.addAttribute("reviews", reviewService.getReviewCountForUser(current_user.get()));
		model.addAttribute("categories_written", postService.getCategoryForUser(current_user.get()));
		return "views/users/profile";
	}
	
	@GetMapping("/users/delete/{id}")
	public String delete(@PathVariable("id")Long id) {
		User user = userService.findById(id);
		if(user == null) {
			notifyService.addErrorMessage("Cannot find User #"+ id);
		}else {
			userService.deleteById(id);
		}
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{id}")
	public String edit(@PathVariable("id")Long id, Model model) {
		User user = userService.findById(id);
		if(user == null) {
			notifyService.addErrorMessage("Cannot find User #"+ id);
			return "redirect:/users";
		}
		model.addAttribute("user", user);
		return "views/users/edit";
	}
	
	@PostMapping("/users/edit")
	public String edit( @ModelAttribute User user,  Model model) {
		System.out.println("user goig to be update "+ user);
		User updatedUser = userService.findById(user.getId());
		updatedUser.setFirstName(user.getFirstName());
		updatedUser.setLastName(user.getLastName());
		updatedUser.setEmail(user.getEmail());
		userService.create(updatedUser);
		model.addAttribute("successMessage", "User has been updated.");
		model.addAttribute("user", updatedUser);
		return "/views/users/edit";
	}

}
