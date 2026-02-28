package com.bookstore.repositories;

import com.bookstore.models.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// Cria um Repository extendendo o JpaRepo e passa pra ele de qual entidade e esse repositorio e qualo tipo da PK dela
// Resumindo "essa interface e de ReviewModel, que tem sua primary key com o tipo UUID"
@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel, UUID> { }
