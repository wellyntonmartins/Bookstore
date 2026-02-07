package com.bookstore.services;

import com.bookstore.models.AuthorModel;
import com.bookstore.repsitories.AuthorRepository;

import java.util.List;

public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorModel> getAllAuthors() {
        return authorRepository.findAll();
    }
}
