package com.bookstore.services;

import com.bookstore.dtos.ReviewRecordDto;
import com.bookstore.exceptions.DataFormatWrongException;
import com.bookstore.models.BookModel;
import com.bookstore.models.PublisherModel;
import com.bookstore.models.ReviewModel;
import com.bookstore.repsitories.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookService bookService;

    public ReviewService(ReviewRepository reviewRepository, BookService bookService) {
        this.reviewRepository = reviewRepository;
        this.bookService = bookService;
    }

    // -------------- FIND METHODS --------------
    public List<ReviewModel> getAllReviews() {
        return reviewRepository.findAll();
    }

    public ReviewModel getReviewById(UUID id) {
        if (id == null) {
            throw new DataFormatWrongException("The provided UUID can't be empty or null.");
        }

        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review with UUID: '" + id + "' not found."));
    }

    // -------------- SAVE/UPDATE METHODS --------------
    @Transactional
    public ReviewModel saveReview(UUID bookId, ReviewRecordDto reviewRecordDto) {
        if (!StringUtils.hasText(reviewRecordDto.comment()) || Objects.isNull(bookId)) {
            throw new DataFormatWrongException("Data cannot be empty. Please verify the request content.");
        }

        if (reviewRecordDto.comment().matches("\\d+")) {
            throw new DataFormatWrongException("The comment cannot consist solely of numbers.");
        }

        BookModel foundBook = bookService.getBookById(bookId);
        if (!Objects.isNull(foundBook.getReview())) {
            throw new DataIntegrityViolationException("The referenced Book already have an review. Please, change the book or update it review");
        }

        ReviewModel newReview = new ReviewModel();
        newReview.setComment(reviewRecordDto.comment());
        newReview.setBook(foundBook);
        return reviewRepository.save(newReview);
    }

    @Transactional
    public ReviewModel updateReview(UUID id, ReviewRecordDto reviewRecordDto) {
        if (!StringUtils.hasText(reviewRecordDto.comment())) {
            throw new DataFormatWrongException("Data cannot be empty. Please verify the request content.");
        }

        if (reviewRecordDto.comment().matches("\\d+")) {
            throw new DataFormatWrongException("The comment cannot consist solely of numbers.");
        }

        ReviewModel reviewToUpdate = getReviewById(id);

        reviewToUpdate.setComment(reviewRecordDto.comment());
        return reviewRepository.save(reviewToUpdate);
    }
}
