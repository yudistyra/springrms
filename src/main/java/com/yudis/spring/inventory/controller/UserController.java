package com.yudis.spring.inventory.controller;

import java.util.List; 

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yudis.spring.inventory.model.Role;
import com.yudis.spring.inventory.model.User;
import com.yudis.spring.inventory.service.ProductService;
import com.yudis.spring.inventory.service.RoleService;
import com.yudis.spring.inventory.service.UserService;
import com.yudis.spring.inventory.service.WarehouseService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ProductService productService;
	@Autowired
	private WarehouseService warehouseService;
	
	@GetMapping("/login")
	public String login(){
        return "login";
    }
	
	@GetMapping("/register")
	public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
	}
	
	@PostMapping("/register")
	public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
        
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else {
            String result = userService.create(user);
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
	public String home(Model model){
		int inactive = userService.findAllUserByActive(false).size();
		int alluser = userService.findAllUser().size();
		int allproduct = productService.findAllProduct().size();
		int allwarehouse = warehouseService.findAll().size(); 
		
		model.addAttribute("pendingUser", inactive);
		model.addAttribute("allUser", alluser);
		model.addAttribute("allProduct", allproduct);
		model.addAttribute("allWarehouse", allwarehouse);
        return "home";
    }
	
	@GetMapping("/admin/users")
	public String users(Model model){
		model.addAttribute("users", userService.findAllUser());
        return "admin/users";
    }
	
	@GetMapping("/admin/users/data")
	@ResponseBody
	public String usersJsonData(){
		JSONObject users = new JSONObject();
		
		users.put("draw", 1);
		users.put("recordsTotal", userService.findAllUser().size());
		users.put("recordsFiltered", userService.findAllUser().size());
		users.put("data", userService.findAllUser());
		
		return users.toString();
    }
	
	@GetMapping("/admin/users/edit/{id}")
	public String users(@PathVariable String id, Model model){
		User user = userService.findUserById(Long.parseLong(id));
		List<Role> roles = roleService.findAllRole();
		model.addAttribute("user", user);
		model.addAttribute("roleList", roles);
		
		return "admin/useredit";
    }
	
	@PostMapping("/admin/users/edit/submit")
	public String updateUser(User user, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			String result = userService.update(user);
            
            if(result.equalsIgnoreCase("success"))
            	return "redirect:/admin/users";
            	
        }
		
		return "admin/useredit";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied(){
        return "access-denied";
    }
}
