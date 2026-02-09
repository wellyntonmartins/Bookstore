package com.bookstore.services;

import com.bookstore.exceptions.DataFormatWrongException;
import com.bookstore.models.AuthorModel;
import com.bookstore.models.BookModel;
import com.bookstore.repsitories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // -------------- FIND METHODS --------------
    public List<AuthorModel> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<AuthorModel> getAllAuthorsByContainingName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new DataFormatWrongException("Data cannot be empty. Please verify the request content.");
        }

        List<AuthorModel> authors = authorRepository.findAuthorModelsByNameContainingIgnoreCase(name);

        if (authors.isEmpty()) {
            throw new EntityNotFoundException("Books not found. Please check the provided title");
        }

        return authors;
    }

    public AuthorModel getAuthorById(UUID id) {
        if (id == null) {
            throw new DataFormatWrongException("The provided UUID can't be empty or null.");
        }

        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with UUID: '" + id + "' not found"));
    }

    // -------------- SAVE/EDIT/EXCLUDE METHODS --------------
}
