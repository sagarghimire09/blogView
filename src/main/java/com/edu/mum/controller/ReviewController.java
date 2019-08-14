package com.edu.mum.controller;

import com.edu.mum.domain.Post;
import com.edu.mum.domain.Review;
import com.edu.mum.domain.User;
import com.edu.mum.service.PostService;
import com.edu.mum.service.ReviewService;
import com.edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;


    @GetMapping("/posts/review/{id}")
    public String upateRating(@PathVariable("id") Long postId,
                              @RequestParam(value = "rating") String rating,
                              RedirectAttributes redirectAttributes, Principal principal
    ){
        Review review = new Review();
            review.setRating(Integer.parseInt(rating));

        if (postId != null){
            System.out.println("post id ="+ postId);
            Optional<Post> reviewedPost = postService.findById(postId);
            review.setPost(reviewedPost.get());
//            Optional<User> reviewer = SessionUtils.getCurrentUser();

                Optional<User> user = userService.findByUsername(principal.getName());
                if (!user.isPresent()){
                    redirectAttributes.addFlashAttribute("msg","Please logged in first.");
                    return "redirect:/posts/view/{id}";
                }
                Optional<Review> duplicateReview = reviewService.findByUserAndPost(user.get(),reviewedPost.get());

                if (duplicateReview.isPresent()){
                    redirectAttributes.addFlashAttribute("msg","Your review has already been recorded.");
                    return "redirect:/posts/view/{id}";
                }
            review.setUser(user.get());
            reviewService.save(review);
            redirectAttributes.addFlashAttribute("msg","Thank you for review.");
            System.out.println("review saved!!!!!!");
//            }

        }

        return "redirect:/posts/view/{id}";
    }
}
