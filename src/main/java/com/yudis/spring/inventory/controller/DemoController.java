package com.yudis.spring.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {
	
	@GetMapping("/login")
	public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
	
	@GetMapping("/home")
	public String home(){
        return "index";
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
