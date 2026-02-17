package com.bookstore.services;

import com.bookstore.dtos.AuthorRecordDto;
import com.bookstore.exceptions.DataFormatWrongException;
import com.bookstore.models.AuthorModel;
import com.bookstore.repsitories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
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
            throw new EntityNotFoundException("Books not found. Please check the provided title.");
        }

        return authors;
    }

    public AuthorModel getAuthorById(UUID id) {
        if (id == null) {
            throw new DataFormatWrongException("The provided UUID can't be empty or null.");
        }

        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with UUID: '" + id + "' not found."));
    }

    // -------------- SAVE/UPDATE/EXCLUDE METHODS --------------
    @Transactional
    public AuthorModel saveAuthor(AuthorRecordDto authorRecordDto) {
        if (!StringUtils.hasText(authorRecordDto.name())) {
            throw new DataFormatWrongException("Data cannot be empty. Please verify the request content.");
        }

        if (authorRecordDto.name().matches("\\d+")) {
            throw new DataFormatWrongException("The name cannot consist solely of numbers.");
        }

        Optional<AuthorModel> authorToCompare = authorRepository.findAuthorModelByName(authorRecordDto.name());

        if (authorToCompare.isPresent()) {
            throw new DataIntegrityViolationException("Already exists an author with this name. Please change the name of new author.");
        }

        AuthorModel newAuthor = new AuthorModel();
        newAuthor.setName(authorRecordDto.name());

        return authorRepository.save(newAuthor);
    }

    @Transactional
    public AuthorModel updateAuthor(UUID authorId, AuthorRecordDto authorRecordDto) {
        if (!StringUtils.hasText(authorRecordDto.name())) {
            throw new DataFormatWrongException("Data cannot be empty. Please verify the request content.");
        }

        if (authorRecordDto.name().matches("\\d+")) {
            throw new DataFormatWrongException("The name cannot consist solely of numbers.");
        }

        AuthorModel authorToUpdate = getAuthorById(authorId);
        Optional<AuthorModel> authorToCompare = authorRepository.findAuthorModelByName(authorRecordDto.name());

        if (authorToCompare.isPresent() && !authorToCompare.get().getId().equals(authorToUpdate.getId())) {
            throw new DataIntegrityViolationException("Already exists an author with this name. Please change the name to update this author.");
        }

        authorToUpdate.setName(authorRecordDto.name());

        return authorRepository.save(authorToUpdate);
    }

    @Transactional
    public void deleteAuthor(UUID id) {
        getAuthorById(id);
        authorRepository.deleteById(id);
    }
}
