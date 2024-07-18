package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
	private final RestaurantRepository restaurantRepository;        
    
    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;            
    }     
  
    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword,
                        @RequestParam(name = "address", required = false) String address,
                        @RequestParam(name = "price", required = false) Integer price, 
                        @RequestParam(name = "category", required = false) String category,
                        @RequestParam(name = "order", required = false) String order,
                        @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
                        Model model) 
    {
        Page<Restaurant> restaurantPage;
                
        if (keyword != null && !keyword.isEmpty()) {
        	if (order != null && order.equals("priceAsc")) {
                restaurantPage = restaurantRepository.findByNameLikeOrAddressLikeOrderByPriceAsc("%" + keyword + "%", "%" + keyword + "%", pageable);
            } else {
                restaurantPage = restaurantRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%", "%" + keyword + "%", pageable);
            } 
        } else if (address != null && !address.isEmpty()) {
        	if (order != null && order.equals("priceAsc")) {
                restaurantPage = restaurantRepository.findByAddressLikeOrderByPriceAsc("%" + address + "%", pageable);
            } else {
                restaurantPage = restaurantRepository.findByAddressLikeOrderByCreatedAtDesc("%" + address + "%", pageable);
            }  
        } else if (price != null) {
        	if (order != null && order.equals("priceAsc")) {
                restaurantPage = restaurantRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
            } else {
                restaurantPage = restaurantRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);
            }
        } else if (category != null && !category.isEmpty()) {
        	restaurantPage = restaurantRepository.findByCategoryNameContaining(category, pageable);
        } else {
        	if (order != null && order.equals("priceAsc")) {
                restaurantPage = restaurantRepository.findAllByOrderByPriceAsc(pageable);
            } else {
                restaurantPage = restaurantRepository.findAllByOrderByCreatedAtDesc(pageable);   
            }
        }                
        
        model.addAttribute("restaurantPage", restaurantPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("address", address);
        model.addAttribute("price", price);
        model.addAttribute("category", category);
        model.addAttribute("order", order);
        
        return "restaurants/index";
    }
    
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        Restaurant restaurant = restaurantRepository.getReferenceById(id);
        
        model.addAttribute("restaurant", restaurant);         
        
        return "restaurants/show";
    }    
}
