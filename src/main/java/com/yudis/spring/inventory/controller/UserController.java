package com.yudis.spring.inventory.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yudis.spring.inventory.model.User;
import com.yudis.spring.inventory.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
	
	@GetMapping("/register")
	public ModelAndView register() {
		ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
	}
	
	@PostMapping("/register")
	public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
        
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            String result = userService.register(user);
            if(result.equalsIgnoreCase("success")) {
	            modelAndView.addObject("successMessage", "User has been registered successfully");
	            modelAndView.addObject("user", new User());
	            modelAndView.setViewName("register");
            }
            else if(result.equalsIgnoreCase("failed")) {
            	modelAndView.addObject("successMessage", "User failed to register");
	            modelAndView.addObject("user", new User());
	            modelAndView.setViewName("register");
            }
            else {
            	modelAndView.addObject("successMessage", result);
	            modelAndView.addObject("user", new User());
	            modelAndView.setViewName("register");
            }

        }
        return modelAndView;
	}
	
	@GetMapping("/home")
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		int inactive = userService.findAllUserByActive(0).size();
		int alluser = userService.findAllUser().size();
		modelAndView.addObject("pendingUser", inactive);
		modelAndView.addObject("allUser", alluser);
        modelAndView.setViewName("home");
        return modelAndView;
    }
	
	@GetMapping("/admin")
	public String admin(){
        return "admin";
    }
	
	@GetMapping("/manager")
	public String manager(){
        return "manager";
    }
	
	@GetMapping("/access-denied")
	public String accessDenied(){
        return "access-denied";
    }
}
