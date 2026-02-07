package com.bookstore.repsitories;

import com.bookstore.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Cria um Repository extendendo o JpaRepo e passa pra ele de qual entidade e esse repositorio e qualo tipo da PK dela
// Resumindo "essa interface e de BookModel, que tem sua primary key com o tipo UUID"
public interface BookRepository extends JpaRepository<BookModel, UUID> {
    // Exact search
    Optional<BookModel> findBookModelByTitle(String title);

    // Search all books with title part (Ex: search "Harry", can return "Harry Potter")
    List<BookModel> findBookModelsByTitleContainingIgnoreCase(String title);
}
