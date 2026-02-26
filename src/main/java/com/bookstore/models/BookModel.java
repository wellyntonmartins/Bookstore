package com.bookstore.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "TB_BOOK")
public class BookModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String title;
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private PublisherModel publisher;
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany//(fetch = FetchType.LAZY)
    @JoinTable( // Por conta de ser um relacionamento de ManyToMany, e preciso criar uma tabela de associacao para
            // suportar esses dados, cujas DUAS COLUNAS SAO FOREIGN KEY UMAS DAS OUTRAS
            joinColumns = @JoinColumn(name = "book_id"),// Primeira coluna, com o primary key da entidade atual (tb_book)
            inverseJoinColumns = @JoinColumn(name = "author_id")// Segunda coluna, com o primary key da entidade
            // referenciada na lista abaixo
    )
    private Set<AuthorModel> authors = new HashSet<>();
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private ReviewModel review;

    @Column(nullable = false)
    private int available_quantity = 0;
}
