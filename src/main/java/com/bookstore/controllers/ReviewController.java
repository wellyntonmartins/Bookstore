package com.bookstore.controllers;

import com.bookstore.dtos.ReviewRecordDto;
import com.bookstore.models.ReviewModel;
import com.bookstore.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("bookstore/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewModel>> getAllReviews() {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewModel> getReviewById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReviewById(id));
    }

    @PostMapping
    public ResponseEntity<ReviewModel> save(@RequestParam(required = true) UUID bookId, @RequestBody ReviewRecordDto reviewRecordDto) {
        ReviewModel response = reviewService.saveReview(bookId, reviewRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewModel> update(@PathVariable UUID id, @RequestBody ReviewRecordDto reviewRecordDto) {
        ReviewModel response = reviewService.updateReview(id, reviewRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
