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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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



    // -------------- SAVE/EDIT/EXCLUDE METHODS --------------
    @Transactional // Faz com que caso aconteca algum erro ao salvar/exception, ela da um RollBack nas transações
    public BookModel saveBook(BookRecordDto bookRecordDto) {
        if (!StringUtils.hasText(bookRecordDto.title()) || bookRecordDto.authorsIds().isEmpty()) {
            throw new DataFormatWrongException("Dados não podem ser vazios. Por favor, verifique o conteúdo da requisição");
        }

        if (bookRecordDto.title().matches("\\d+")) {
            System.out.println("bookRecordDto: " + bookRecordDto.toString());
            throw new DataFormatWrongException("O título não pode ser composto apenas por números.");
        }

        if (Objects.isNull(bookRecordDto.publisherId())) {
            throw new DataFormatWrongException("Para adicionar um livro, e necessário informar a editora.");
        }

        PublisherModel publisher = publisherRepository.findById(bookRecordDto.publisherId()).orElseThrow(() ->
                new EntityNotFoundException("Editora nao encontrada. Por favor, verifique o UUID dela fornecido"));
        List<AuthorModel> authors = authorRepository.findAllById(bookRecordDto.authorsIds());


        if (authors.size() != bookRecordDto.authorsIds().size()) {
            throw new EntityNotFoundException("Um ou mais autores não foram encontrados. Por favor, verifique os UUID's deles enviados.");
        }

        BookModel newBook = new BookModel();
        newBook.setTitle(bookRecordDto.title());
        newBook.setPublisher(publisher);
        newBook.setAuthors(new HashSet<>(authors));

        if(StringUtils.hasText(bookRecordDto.reviewComment())) {
            ReviewModel reviewModel = new ReviewModel();
            reviewModel.setComment(bookRecordDto.reviewComment());
            reviewModel.setBook(newBook);
            newBook.setReview(reviewModel);
        }

        return bookRepository.save(newBook);
    }

    @Transactional
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}
