package com.bookstore.models;

import com.bookstore.enums.Shift;
import jakarta.persistence.*;
import org.hibernate.type.descriptor.java.BigIntegerJavaType;

import java.io.Serializable;
import java.util.UUID;

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

    public StudentModel() {
    }

    public StudentModel(UUID id, String name, Integer matriculation, Shift shift, String school_class) {
        this.id = id;
        this.name = name;
        this.matriculation = matriculation;
        this.shift = shift;
        this.school_class = school_class;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMatriculation() {
        return matriculation;
    }

    public void setMatriculation(Integer matriculation) {
        this.matriculation = matriculation;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public String getSchool_class() {
        return school_class;
    }

    public void setSchool_class(String school_class) {
        this.school_class = school_class;
    }
}
