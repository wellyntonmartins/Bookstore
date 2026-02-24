package com.bookstore.services;

import com.bookstore.dtos.StudentRecordDto;
import com.bookstore.exceptions.DataFormatWrongException;
import com.bookstore.models.StudentModel;
import com.bookstore.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentModel> getAllStudents() {
        return studentRepository.findAll();
    }

    public StudentModel getStudentById(UUID id) {
        if (id == null) {
            throw new DataFormatWrongException("The provided UUID can't be empty or null.");
        }

        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with UUID: '" + id + "' not found."));
    }

    @Transactional
    public StudentModel saveStudent(StudentRecordDto studentRecordDto) {
        if (!StringUtils.hasText(studentRecordDto.name()) || !StringUtils.hasText(studentRecordDto.school_class())) {
            throw new DataFormatWrongException("Data cannot be empty. Please verify the request content.");
        }

        if (studentRecordDto.name().matches("\\d+") || studentRecordDto.school_class().matches("\\d+")) {
            throw new DataFormatWrongException("The name and/or school_class cannot consist solely of numbers.");
        }

        if (studentRecordDto.matriculation() <= 0 ) {
            throw new DataFormatWrongException("The number of matriculation cannot be below or equals 0.");
        }

        StudentModel newStudent = new StudentModel();
        newStudent.setName(studentRecordDto.name());
        newStudent.setMatriculation(studentRecordDto.matriculation());
        newStudent.setShift(studentRecordDto.shift());
        newStudent.setSchool_class(studentRecordDto.school_class());

        return studentRepository.save(newStudent);
    }

    @Transactional
    public StudentModel updateStudent(UUID id, StudentRecordDto studentRecordDto) {
        if (!StringUtils.hasText(studentRecordDto.name()) || !StringUtils.hasText(studentRecordDto.school_class())) {
            throw new DataFormatWrongException("Data cannot be empty. Please verify the request content.");
        }

        if (studentRecordDto.name().matches("\\d+") || studentRecordDto.school_class().matches("\\d+")) {
            throw new DataFormatWrongException("The name and/or school_class cannot consist solely of numbers.");
        }

        if (studentRecordDto.matriculation() <= 0 ) {
            throw new DataFormatWrongException("The number of matriculation cannot be below or equals 0.");
        }

        StudentModel studentToUpdate = getStudentById(id);
        List<StudentModel> studentToCompare = studentRepository.findStudentModelByMatriculation(studentToUpdate.getMatriculation());

        if (!studentToCompare.isEmpty() && !studentToCompare.get(0).getName().equals(studentRecordDto.name())) {
            throw new DataIntegrityViolationException("Already exists an student with this matriculation. Please change the matriculation for update this student.");
        }

        studentToUpdate.setName(studentRecordDto.name());
        studentToUpdate.setMatriculation(studentRecordDto.matriculation());
        studentToUpdate.setShift(studentRecordDto.shift());
        studentToUpdate.setSchool_class(studentRecordDto.school_class());

        return studentRepository.save(studentToUpdate);
    }
}
