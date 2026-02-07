package com.bookstore.repsitories;

import com.bookstore.models.PublisherModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// Cria um Repository extendendo o JpaRepo e passa pra ele de qual entidade e esse repositorio e qualo tipo da PK dela
// Resumindo "essa interface e de PublisherModel, que tem sua primary key com o tipo UUID"
public interface PublisherRepository extends JpaRepository<PublisherModel, UUID> {

}
