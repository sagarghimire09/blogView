package com.edu.mum.controller;

import com.edu.mum.domain.Comment;
import com.edu.mum.domain.Post;
import com.edu.mum.domain.User;
import com.edu.mum.service.PostService;
import com.edu.mum.service.UserService;
import com.edu.mum.util.ArithmeticUtils;
import com.edu.mum.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/fullSearch")
    public String fullSearch(@RequestParam(defaultValue = "0") int page,
                             @RequestParam("searchParameter") String searchParameter, Model model){
        List<Post> latest5Posts = this.postService.findLatest5();
        model.addAttribute("latest5Posts", latest5Posts);


        Page<Post> posts = postService.findAllByTitleContainingIgnoreCaseOrUser_FirstNameContainingIgnoreCase(searchParameter,searchParameter,page);
        Pager pager = new Pager(posts);
        model.addAttribute("avgRatingMap", ArithmeticUtils.getAvgRatingMap(postService.findAll()));
        model.addAttribute("pager", pager);
        model.addAttribute("comment", new Comment());
        return "views/home/index";

    }


    @GetMapping("/searchPost")
    public String searchPost(@RequestParam(defaultValue = "0") int page,
                             @RequestParam("searchParameter") String searchParameter, Model model){
        Page<Post> posts = postService.findAllByTitleContainingIgnoreCaseOrUser_FirstNameContainingIgnoreCase(searchParameter,searchParameter,page);
        Pager pager = new Pager(posts);
        model.addAttribute("pager", pager);
        model.addAttribute("avgRatingMap", ArithmeticUtils.getAvgRatingMap(postService.findAll()));
        return "views/posts/postList";
    }

    @GetMapping("/searchUser")
    public String searchUser(@RequestParam(defaultValue = "0") int page,
                             @RequestParam("searchParameter") String searchParameter, Model model){
        Page<User> users = userService.findAllByFirstNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmail(searchParameter,searchParameter,searchParameter,page);
        Pager pager = new Pager(users);
        model.addAttribute("pager", pager);
        return "views/users/userList";
    }
}
