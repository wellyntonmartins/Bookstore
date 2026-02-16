package com.bookstore.controllers;

import com.bookstore.dtos.BookRecordDto;
import com.bookstore.models.BookModel;
import com.bookstore.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("bookstore/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookModel>> getBooks(@RequestParam(required = false) String title) {
        if (title != null) {
            return ResponseEntity.ok(bookService.getAllBooksByContainingTitle(title));
        }
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookModel> getBookById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<BookModel> save(@RequestBody BookRecordDto bookRecordDto) {
        BookModel response = bookService.saveBook(bookRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookModel> update(@PathVariable UUID id, @RequestBody BookRecordDto bookRecordDto) {
        BookModel response = bookService.updateBook(id, bookRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<BookModel> updatePatchBook(@PathVariable UUID id, @RequestBody BookRecordDto bookRecordDto) {
//
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooks(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Book with id '" + id + "' deleted successfully.");
    }
}

