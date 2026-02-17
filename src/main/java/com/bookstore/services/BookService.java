package com.bookstore.services;

import com.bookstore.dtos.BookRecordDto;
import com.bookstore.exceptions.DataFormatWrongException;
import com.bookstore.models.AuthorModel;
import com.bookstore.models.BookModel;
import com.bookstore.models.PublisherModel;
import com.bookstore.models.ReviewModel;
import com.bookstore.repsitories.AuthorRepository;
import com.bookstore.repsitories.BookRepository;
import com.bookstore.repsitories.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    // -------------- FIND METHODS --------------
    public List<BookModel> getAllBooks() {
        return bookRepository.findAll();
    }

    public BookModel getBookById(UUID id) {
        if (id == null) {
            throw new DataFormatWrongException("The provided UUID can't be empty or null.");
        }

        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with UUID: '" + id + "' not found."));
    }

    public List<BookModel> getAllBooksByContainingTitle(String title) {
        if (!StringUtils.hasText(title)) {
            throw new DataFormatWrongException("Data cannot be empty. Please verify the request content.");
        }

        List<BookModel> books = bookRepository.findBookModelsByTitleContainingIgnoreCase(title);

        if (books.isEmpty()) {
            throw new EntityNotFoundException("Books not found. Please check the provided title.");
        }

        return books;
    }


    // -------------- SAVE/UPDATE/EXCLUDE METHODS --------------
    @Transactional
    public BookModel saveBook(BookRecordDto bookRecordDto) {
        // Validation for empty or null data
        if (!StringUtils.hasText(bookRecordDto.title()) || bookRecordDto.authorsIds().isEmpty()) {
            throw new DataFormatWrongException("Data cannot be empty. Please verify the request content.");
        }

        if (bookRecordDto.title().matches("\\d+")) {
            throw new DataFormatWrongException("The title cannot consist solely of numbers.");
        }

        if (Objects.isNull(bookRecordDto.publisherId())) {
            throw new DataFormatWrongException("To add a book, you need to inform the publisher.");
        }

        // Validation for data not found or conflict on entities.
        Optional<BookModel> bookToCompare = bookRepository.findBookModelByTitle(bookRecordDto.title());

        if (bookToCompare.isPresent()) { // Check if book with this title exists
            throw new DataIntegrityViolationException("Already exists an book with this title. Please change the title of new book.");
        }

        PublisherModel publisher = publisherRepository.findById(bookRecordDto.publisherId()).orElseThrow(() ->
                new EntityNotFoundException("Publisher not found. Please check the provided UUID."));
        List<AuthorModel> authors = authorRepository.findAllById(bookRecordDto.authorsIds());

        if (authors.isEmpty()) {
            throw new EntityNotFoundException("Any author was found with provided UUID's. Please check they provided UUID's.");
        }

        if (authors.size() != bookRecordDto.authorsIds().size()) {
            throw new EntityNotFoundException("One or more authors was not found. Please check they provided UUID's.");
        }

        // Instantiation
        BookModel newBook = new BookModel();
        newBook.setTitle(bookRecordDto.title());
        newBook.setPublisher(publisher);
        newBook.setAuthors(new HashSet<>(authors));

        if (StringUtils.hasText(bookRecordDto.reviewComment())) {
            ReviewModel reviewModel = new ReviewModel();
            reviewModel.setComment(bookRecordDto.reviewComment());
            reviewModel.setBook(newBook);
            newBook.setReview(reviewModel);
        }

        return bookRepository.save(newBook);
    }

    @Transactional
    public BookModel updateBook(UUID bookId, BookRecordDto bookRecordDto) {
        // Validation for empty or null data
        if (Objects.isNull(bookId)) {
            throw new DataFormatWrongException("To update an book, you need to inform the book UUID. Please, check the provided book UUID.");
        }

        if (!StringUtils.hasText(bookRecordDto.title()) || bookRecordDto.authorsIds().isEmpty()) {
            throw new DataFormatWrongException("Data cannot be empty. Please verify the request content.");
        }

        if (bookRecordDto.title().matches("\\d+")) {
            throw new DataFormatWrongException("The title cannot consist solely of numbers.");
        }

        if (Objects.isNull(bookRecordDto.publisherId())) {
            throw new DataFormatWrongException("To update an book, you need to inform the publisher.");
        }

        // Validation for data not found or conflict on entities.
        BookModel bookToUpdate = getBookById(bookId); // If it pass, is because the provided bookId is of an existing book

        Optional<BookModel> bookToCompare = bookRepository.findBookModelByTitle(bookRecordDto.title());

        if (bookToCompare.isPresent() && !bookToCompare.get().getId().equals(bookToUpdate.getId())) {
            throw new DataIntegrityViolationException("Already exists an book with this title. Please change the title for update this book.");
        }

        PublisherModel publisher = publisherRepository.findById(bookRecordDto.publisherId()).orElseThrow(() ->
                new EntityNotFoundException("Publisher not found. Please check the provided UUID."));

        List<AuthorModel> authors = authorRepository.findAllById(bookRecordDto.authorsIds());

        if (authors.isEmpty()) {
            throw new EntityNotFoundException("Any author was found with provided UUID's. Please check they provided UUID's.");
        }

        if (authors.size() != bookRecordDto.authorsIds().size()) {
            throw new EntityNotFoundException("One or more authors was not found. Please check they provided UUID's.");
        }

        bookToUpdate.setTitle(bookRecordDto.title());
        bookToUpdate.setPublisher(publisher);
        bookToUpdate.setAuthors(new HashSet<>(authors));

        if (StringUtils.hasText(bookRecordDto.reviewComment())) {
            ReviewModel bookAtualReview = bookToUpdate.getReview();

            // Check if the book has an review to put
            if (!Objects.isNull(bookAtualReview)) {
                bookAtualReview.setComment(bookRecordDto.reviewComment());
            } else {
                ReviewModel reviewModel = new ReviewModel();
                reviewModel.setComment(bookRecordDto.reviewComment());
                reviewModel.setBook(bookToUpdate);
                bookToUpdate.setReview(reviewModel);
            }
        }

        return bookRepository.save(bookToUpdate);
    }

    @Transactional
    public void deleteBook(UUID id) {
        getBookById(id); // To check if this book exists
        bookRepository.deleteById(id); // If an error occurs, will be caught by DataIntegrityViolationException
    }
}
