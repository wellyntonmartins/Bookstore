package com.bookstore.controllers;

import com.bookstore.dtos.LoanRecordDto;
import com.bookstore.models.LoanModel;
import com.bookstore.services.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("bookstore/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<LoanModel>> getLoans() {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getAllLoans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanModel> getLoanById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getLoanById(id));
    }

    @PostMapping
    public ResponseEntity<LoanModel> save(@RequestBody LoanRecordDto loanRecordDto) {
        LoanModel response = loanService.saveLoan(loanRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanModel> update(@PathVariable UUID id, @RequestBody LoanRecordDto loanRecordDto) {
        LoanModel response = loanService.updateLoan(id, loanRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
