package com.bookstore.controllers;

import com.bookstore.dtos.StudentRecordDto;
import com.bookstore.models.StudentModel;
import com.bookstore.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("bookstore/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentModel>> getAllStudents() {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentModel> getStudentById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<StudentModel> save(@RequestBody StudentRecordDto studentRecordDto) {
        StudentModel response = studentService.saveStudent(studentRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentModel> update(@PathVariable UUID id, @RequestBody StudentRecordDto studentRecordDto) {
        StudentModel response = studentService.updateStudent(id, studentRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
