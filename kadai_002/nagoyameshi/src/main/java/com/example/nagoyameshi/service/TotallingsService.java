package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;

import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class TotallingsService {
	 private final UserRepository userRepository;
     private final RestaurantRepository restaurantRepository;
   
   public TotallingsService (UserRepository userRepository, RestaurantRepository restaurantRepository) {
  	 this.userRepository = userRepository;
  	 this.restaurantRepository = restaurantRepository;	 
   }
   
   public Integer countAllUsers() {
	   return userRepository.countAllUsers();
   }
   
   public Integer countAllRestaurants() {
	   return restaurantRepository.countAllRestaurants();
   }
}
