package com.bookstore.models;

import com.bookstore.enums.Shift;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.descriptor.java.BigIntegerJavaType;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "TB_STUDENT")
public class StudentModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private Integer matriculation;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Shift shift;
    @Column(nullable = false)
    private String school_class;
}
