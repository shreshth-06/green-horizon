package com.greenhorizon.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenhorizon.main.entity.User;
import com.greenhorizon.main.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public String loginPage(@ModelAttribute("user") User user, Model model) {
	    model.addAttribute("user", new User()); // add empty User model attribute before rendering login.html
	    return "login";
	}


	    @GetMapping("/signup")
	    public String showSignupForm(Model model) 
	    {
	    	 model.addAttribute("user", new User());
	        return "signup"; // Thymeleaf template name
	    }
	    
	    @PostMapping("/signingUp") 
	    public String signupValidate(@ModelAttribute("user") User user) 
	    {
	    	  
	    	  	System.out.println("Sign Up User Dets :: "+user.toString());
	    	  	
	    	  userService.registerUser(user);
	    	  
	        return "login"; // Thymeleaf template name
	    }
	    
	    
	    
	    
	    

	    @PostMapping("/loginPage")
	    public String processLogin(@ModelAttribute("user") User user, Model model) 
	    {
	        User u = userService.findByEmail(user.getEmail());
	        
	        if (u == null || !u.getPassword().equals(user.getPassword())) {
	            model.addAttribute("error", "Invalid email or password");
	            return "login";
	        }
	     // On successful login, redirect to /home mapping (controller and view)
	        return "redirect:/home"; 
	    }
   
	}



