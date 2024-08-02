package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.service.TotallingsService;

@Controller
@RequestMapping("/admin/aggregation/totallings")
public class AdminTotallingController {
     private final TotallingsService totallingsService;
     
     public AdminTotallingController (TotallingsService totallingsService) {
    	 this.totallingsService = totallingsService;
     }
     
     @GetMapping
     public String totallings(Model model) {
    	 Integer totalUsers = totallingsService.countAllUsers();
    	 Integer totalRestaurants = totallingsService.countAllRestaurants();
    
    	 model.addAttribute("totalUsers", totalUsers);
    	 model.addAttribute("totalRestaurants", totalRestaurants);
    	 
    	 return "/admin/aggregation/totallings";
     }

	
}
