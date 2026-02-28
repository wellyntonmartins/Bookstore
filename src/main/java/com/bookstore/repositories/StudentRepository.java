package com.bookstore.repositories;

import com.bookstore.models.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, UUID> {
    List<StudentModel> findStudentModelByMatriculation(Integer matriculation);
}
