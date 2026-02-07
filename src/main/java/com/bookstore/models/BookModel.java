package com.bookstore.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "TB_BOOK")
public class BookModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String title;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY) // Fala pro JPA que essa coluna e uma "ManyToOne" (Basicamente, diz que essa entidade, nessa coluna, pode
    // ter apenas UM publisher)
    @JoinColumn(name = "publisher_id") // Cria uma nova coluna (com o nome "publisher_id") com base na chave primaria da
    // entidade referenciada
    private PublisherModel publisher;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( // Por conta de ser um relacionamento de ManyToMany, e preciso criar uma tabela de associacao para
            // suportar esses dados, cujas DUAS COLUNAS SAO FOREIGN KEY UMAS DAS OUTRAS
            joinColumns = @JoinColumn(name = "book_id"),// Primeira coluna, com o primary key da entidade atual (tb_book)
            inverseJoinColumns = @JoinColumn(name = "author_id")// Segunda coluna, com o primary key da entidade
            // referenciada na lista abaixo
    )
    private Set<AuthorModel> authors = new HashSet<>();

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private ReviewModel review;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PublisherModel getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherModel publisher) {
        this.publisher = publisher;
    }

    public Set<AuthorModel> getAuthors() {
        return authors;
    }

    public ReviewModel getReview() {
        return review;
    }

    public void setReview(ReviewModel review) {
        this.review = review;
    }

    public void setAuthors(Set<AuthorModel> authors) {
        this.authors = authors;
    }
}
