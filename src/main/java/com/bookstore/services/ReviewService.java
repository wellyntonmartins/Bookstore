package com.bookstore.services;

import com.bookstore.models.ReviewModel;
import com.bookstore.repsitories.ReviewRepository;

import java.util.List;

public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewModel> getAllReviews() {
        return reviewRepository.findAll();
    }
}
