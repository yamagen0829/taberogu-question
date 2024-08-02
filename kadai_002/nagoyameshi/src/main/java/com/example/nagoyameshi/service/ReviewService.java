package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewForm;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    
    public ReviewService (ReviewRepository reviewRepository, UserRepository userRepository) {
    	this.reviewRepository = reviewRepository;
    	this.userRepository = userRepository;
    }
    
    public void saveReview(Integer restaurantId, ReviewForm reviewForm, String name) {
        Review review = new Review();
        review.setRestaurantId(restaurantId);
        review.setScore(reviewForm.getScore());
        review.setContent(reviewForm.getContent());
		User user = userRepository.findByName(name); 
        
        if (user != null) {
            review.setUser(user);
            reviewRepository.save(review);
        } else {
            // 適切なエラーハンドリングを行う
            throw new IllegalStateException("ログインユーザーが見つかりません。");
        }
    }    
}
