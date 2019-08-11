package com.edu.mum.controller;

import com.edu.mum.domain.Comment;
import com.edu.mum.domain.Post;
import com.edu.mum.service.PostService;
import com.edu.mum.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomePageController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = {"/"},
            method = RequestMethod.GET)
    public String homepage() {
        return "redirect:/index";
    }


    @GetMapping("/index")
    public String home(@RequestParam(defaultValue = "0") int page,
                       Model model) {
        // Get last 5 post
        List<Post> latest5Posts = this.postService.findLatest5();
        // Send results to view model
        model.addAttribute("latest5Posts", latest5Posts);

        Page<Post> posts = postService.findAllOrderedByDatePageable(page);
        Pager pager = new Pager(posts);
        model.addAttribute("pager", pager);
        model.addAttribute("comment", new Comment());
        return "views/home/index";
    }

}

