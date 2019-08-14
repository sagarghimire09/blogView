package com.edu.mum.controller;

import com.edu.mum.domain.Comment;
import com.edu.mum.domain.Post;
import com.edu.mum.service.PostService;
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

    @GetMapping("/fullSearch")
    public String fullSearch(@RequestParam(defaultValue = "0") int page,
                             @RequestParam("searchParameter") String searchParameter, Model model){
        List<Post> latest5Posts = this.postService.findLatest5();
        model.addAttribute("latest5Posts", latest5Posts);


        Page<Post> posts = postService.findAllByTitleContainingIgnoreCaseOrUser_FirstNameContainingIgnoreCase(searchParameter,searchParameter,page);
        System.out.println("searched post list");
        System.out.println(posts);
        Pager pager = new Pager(posts);
        model.addAttribute("avgRatingMap", ArithmeticUtils.getAvgRatingMap(postService.findAll()));
        model.addAttribute("pager", pager);
        model.addAttribute("comment", new Comment());
        return "views/home/index";


//        return "redirect:/index";

    }
}
