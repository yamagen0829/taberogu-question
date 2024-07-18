package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.service.RestaurantService;
import com.example.nagoyameshi.service.ReviewService;

@Controller
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final RestaurantService restaurantService;
    
    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository, RestaurantService restaurantService) {
    	this.reviewService = reviewService;
    	this.reviewRepository = reviewRepository;
    	this.restaurantService = restaurantService;
    }
    
    @GetMapping("/restaurants/show")
    public String show(@RequestParam("id") Integer restaurantId, Model model) {
    	List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);
    	Restaurant restaurant = restaurantService.findById(restaurantId);
    	
    	model.addAttribute("reviews", reviews);
		model.addAttribute("restaurant", restaurant);
    	
    	return "restaurants/show";
    }
        
}
