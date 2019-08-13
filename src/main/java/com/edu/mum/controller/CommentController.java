package com.edu.mum.controller;

import com.edu.mum.domain.Comment;
import com.edu.mum.domain.Post;
import com.edu.mum.domain.User;
import com.edu.mum.service.CommentService;
import com.edu.mum.service.PostService;
import com.edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class CommentController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public CommentController(PostService postService, UserService userService, CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
    }

//    @RequestMapping(value = "/createComment", method = RequestMethod.POST)
//    public String createNewPost(@Valid Comment comment,
//                                BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return "/commentForm";
//
//        } else {
//            commentService.save(comment);
//            return "redirect:/post/" + comment.getPost().getId();
//        }
//    }

    @RequestMapping(value = "/commentPost/{id}", method = RequestMethod.POST)
    public String commentPostWithId(@PathVariable Long id, @ModelAttribute Comment comment,
                                    Principal principal,
                                    Model model) {
        Optional<Post> post = postService.findById(id);

        if (post.isPresent()) {
            System.out.println("post present");
            Optional<User> user = userService.findByUsername(principal.getName());

            if (user.isPresent()) {
                System.out.println("user present");
                Comment newComment = new Comment();
                newComment.setUser(user.get());
                newComment.setPost(post.get());
                newComment.setBody(comment.getBody());
                commentService.save(newComment);
                model.addAttribute("comment", comment);

                return "redirect:/posts/view/{id}";

            } else {
                return "/error";
            }

        } else {
            return "/error";
        }
    }

//    @RequestMapping(value = "/saveComment" , method = RequestMethod.POST)
//    public String saveComment(){
//        return "views/home/index";
//    }

}
