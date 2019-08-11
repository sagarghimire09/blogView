package com.edu.mum.controller;

import com.edu.mum.domain.User;
import com.edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RegistrationController {

    @Autowired
    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value="/users/register", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("views/users/registeration");
        return modelAndView;
    }

    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> userExists = userService.findByEmail(user.getEmail());
        if (userExists.isPresent()) {
            System.out.println("user exis!!!!!");
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            System.out.println("registraion form has errors !!!!!!!!!!!!!");
            modelAndView.setViewName("views/users/registeration");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.create(user);
            System.out.println("user :"+user +" registered !!!!!");
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("views/users/registeration");

        }
        return modelAndView;
    }

//    @RequestMapping(value = "/registration", method = RequestMethod.GET)
//    public String registration(Model model) {
//
//        model.addAttribute("user", new User());
//        return "/registration";
//    }

//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public String createNewUser(@Valid User user,
//                                BindingResult bindingResult,
//                                Model model) {
//
//        if (userService.findByEmail(user.getEmail()).isPresent()) {
//            bindingResult
//                    .rejectValue("email", "error.user",
//                            "There is already a user registered with the email provided");
//        }
//        if (userService.findByUsername(user.getUsername()).isPresent()) {
//            bindingResult
//                    .rejectValue("username", "error.user",
//                            "There is already a user registered with the username provided");
//        }
//
//        if (!bindingResult.hasErrors()) {
//            // Registration successful, save user
//            // Set user role to USER and set it as active
//            userService.create(user);
//
//            model.addAttribute("successMessage", "User has been registered successfully");
//            model.addAttribute("user", new User());
//        }
//
//        return "/registration";
//    }
}
