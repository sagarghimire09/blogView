package com.edu.mum.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {


//    @RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
//    public String login(){
//        return  "views/users/login";
//    }
//
//    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
//    public String loginProcess(){
//        System.out.println("log in processing method");
//        return  "views/users/index";
//    }
@RequestMapping("/users/login")
public String login(){
    // User doesn't need to re-enter credentials
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if ( (auth instanceof AnonymousAuthenticationToken) ) {
        System.out.println("ins of anonymous user");
        return "views/users/login";
    } else {
        return "redirect:/";
    }
}


}
