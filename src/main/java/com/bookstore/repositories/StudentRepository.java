package com.bookstore.repositories;

import com.bookstore.models.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentModel, UUID> {
    List<StudentModel> findStudentModelByMatriculation(Integer matriculation);
}
