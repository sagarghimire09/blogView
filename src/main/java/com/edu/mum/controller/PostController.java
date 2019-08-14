package com.edu.mum.controller;

import com.edu.mum.domain.*;
import com.edu.mum.service.*;
import com.edu.mum.util.ArithmeticUtils;
import com.edu.mum.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class PostController {
    private final static String upload_dir= System.getProperty("user.dir")+"/src/main/resources/static/uploads/images";
    private final PostService postService;
    private final UserService userService;
    private NotificationService notifyService;
    private CommentService commentService;
    private CategoryService categoryService;

    @Autowired
    public PostController(PostService postService, UserService userService, NotificationService notifyService, CommentService commentService, CategoryService categoryService) {
        this.postService = postService;
        this.userService = userService;
        this.notifyService = notifyService;
        this.commentService = commentService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/posts/create", method = RequestMethod.GET)
    public ModelAndView create(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("post",new Post());
        modelAndView.addObject("categories", categoryService.getAllCategory());
        modelAndView.setViewName("views/posts/create");
        return modelAndView;
    }

    @RequestMapping(value = "/posts/create", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String create(@Valid Post post, @RequestParam("file") MultipartFile imgFile, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("inside post create method");
        if( post.getTitle().isEmpty() ){
            bindingResult.rejectValue("title", "error.post", "Title cannot be empty");
        }
        if( post.getBody().isEmpty() ){System.out.println("body empty !!!!!!!!");

            bindingResult.rejectValue("body", "error.post", "Content cannot be empty");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "There is problem creating this post");
            modelAndView.setViewName("views/posts/create");
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> user = this.userService.findByUsername(auth.getName());
//            System.out.println("logged in user ==="+ user.get());
            if( !user.isPresent() ){
                bindingResult.rejectValue("user", "error.post", "User cannot be null");
            }
//            MultipartFile img = imgFile;
            post.setCoverImage(imgFile.getOriginalFilename());
            Path fileNameAndPath = Paths.get(upload_dir,imgFile.getOriginalFilename());

            Files.write(fileNameAndPath,imgFile.getBytes());

            post.setUser(user.get());
            this.postService.create(post);
            System.out.println("post created !!!!!!!!");
            redirectAttributes.addFlashAttribute("successMessage","Post has been created, we will let you know when aproved");

        }
        return "redirect:/posts/create";

    }
    @RequestMapping("/posts/view/{id}")
    public String view(@PathVariable("id") Long id, Model model){
        Optional<Post> post = this.postService.findById(id);
        if( post.isPresent() ){
            // Get last 5 post
            List<Post> latest5Posts = this.postService.findLatest5();
            // Send results to view model
            model.addAttribute("latest5Posts", latest5Posts);

            model.addAttribute("avgReview", ArithmeticUtils.getAvgRating(post.get().getReviews()));
            model.addAttribute("post", post.get());
            model.addAttribute("latest5comments", commentService.findFirst5ByPost(post.get()));
            model.addAttribute("comment", new Comment());
        }else {
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/error";
        }
        // To have something like src/main/resources/templates/<CONTROLLER-NAME>/<Mapping-Name-view>
        return "views/posts/view";
    }

    /**
     * Remove a post from the database, notify user if post does not exist
     * @param id
     * @return
     */
    @RequestMapping("/posts/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        System.out.println("post to be deleted ="+id);
        Optional<Post> post = this.postService.findById(id);
        if( !post.isPresent()){
            notifyService.addErrorMessage("Cannot find post #" + id);
        } else {
//            postService.deleteById(id);
            postService.delete(post.get());
            System.out.println("post delted");
        }
        return "redirect:/posts";
    }
    /**
     * Display for to edit a post
     * @param id
     * @param model
     * @return
     */
    @RequestMapping( "/posts/edit/{id}" )
    public String edit(@PathVariable("id") Long id, Model model){
        Optional<Post> post = postService.findById(id);
        if( !post.isPresent()  ){
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/posts/";
        }
        Post post1 = post.get();
        model.addAttribute("categories",categoryService.getAllCategory());
        System.out.println("pst to be edited :"+ post1.getUser().getId());
        model.addAttribute("post", post1);
        return "views/posts/edit";
    }
    /**
     * Proceeds to update a post
     * @param post
     * @return
     */
    @RequestMapping(value = "/posts/edit", method = RequestMethod.POST )
    public ModelAndView edit(@Valid @ModelAttribute Post post, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("views/posts/edit");
        // Perform validation
        if( post.getTitle().isEmpty() ){
            bindingResult.rejectValue("title", "error.post", "Title cannot be empty");
        }
        if( post.getBody().isEmpty() ){
            bindingResult.rejectValue("body", "error.post", "Content cannot be empty");
        }
        // Get author

        User user = this.userService.findById(post.getUser().getId());
        if( user==null ){
            bindingResult.rejectValue("user", "error.post", "Author cannot be null");
        }
        if( !bindingResult.hasErrors() ){
            Optional<Post> postOptional = postService.findById(post.getId());
            if (postOptional.isPresent()){
                Post p = postOptional.get();
                p.setUser(user);
                p.setTitle(post.getTitle());
                p.setCategory(post.getCategory());
                p.setBody(post.getBody());
                this.postService.create(p);
                modelAndView.addObject("successMessage", "Post has been updated");
                modelAndView.addObject("post", post);
            }
//            post.setUser(user);

        }
        return modelAndView;
    }
    @RequestMapping("/posts/setStatus/{id}")
    public String setStatus(@PathVariable Long id){
        System.out.println("inside setStatus method");
        Optional<Post> postOptional = postService.findById(id);
        if (postOptional.isPresent()){
            if (!postOptional.get().getStatus()){
                postOptional.get().setStatus(true);
                postService.create(postOptional.get());
                System.out.println("status set to true");
            }
        }

        return "redirect:/posts";

    }

    @RequestMapping("/posts")
    public String index(@RequestParam(defaultValue = "0") int page, Model model){
        Page<Post> posts = this.postService.findAllOrderedByDatePageable(page);
        Pager pager = new Pager(posts);
        model.addAttribute("pager", pager);
        model.addAttribute("avgRatingMap", ArithmeticUtils.getAvgRatingMap(postService.findAll()));
        return "views/posts/postList";
    }


    private boolean isPrincipalOwnerOfPost(Principal principal, Post post) {
        return principal != null && principal.getName().equals(post.getUser().getUsername());
    }

    @RequestMapping("/posts/earning")
    public String showEarning(@RequestParam(defaultValue = "0") int page, Model model){
        Page<Post> posts = this.postService.findAllOrderedByDatePageable(page);

        List<Post> postList = posts.getContent();

        for(Post p: postList){
            p.setEarning(postService.getEarningByPost(p.getId()));
            postService.create(p);
        }
        Pager pager = new Pager(posts);
        model.addAttribute("account", new Account());
        model.addAttribute("avgRatingMap", ArithmeticUtils.getAvgRatingMap(postService.findAll()));
        model.addAttribute("pager", pager);
        return "views/posts/earningPostList";
    }
}
