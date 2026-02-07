package com.bookstore.repsitories;

import com.bookstore.models.AuthorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// Cria um Repository extendendo o JpaRepo e passa pra ele de qual entidade e esse repositorio e qualo tipo da PK dela
// Resumindo "essa interface e de AuthorModel, que tem sua primary key com o tipo UUID"
public interface AuthorRepository extends JpaRepository<AuthorModel, UUID> {

}
