package com.bookstore.controllers;

import com.bookstore.dtos.AuthorRecordDto;
import com.bookstore.models.AuthorModel;
import com.bookstore.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("bookstore/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_LIBRARIAN')")
    public ResponseEntity<List<AuthorModel>> getAuthors(@RequestParam(required = false) String name) {
        if (name != null) {
            return ResponseEntity.ok(authorService.getAllAuthorsByContainingName(name));
        }
        return ResponseEntity.status(HttpStatus.OK).body(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_LIBRARIAN')")
    public ResponseEntity<AuthorModel> getAuthorById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(authorService.getAuthorById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorModel> save(@RequestBody AuthorRecordDto authorRecordDto) {
        AuthorModel response = authorService.saveAuthor(authorRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_LIBRARIAN')")
    public ResponseEntity<AuthorModel> update(@PathVariable UUID id, @RequestBody AuthorRecordDto authorRecordDto) {
        AuthorModel response = authorService.updateAuthor(id, authorRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_LIBRARIAN')")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.status(HttpStatus.OK).body("Author with id '" + id + "' deleted successfully.");
    }
}
