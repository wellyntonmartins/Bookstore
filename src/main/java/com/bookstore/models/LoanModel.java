package com.bookstore.models;

import com.bookstore.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "TB_LOAN")
public class LoanModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookModel book;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentModel student;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalDate due_date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;
}
