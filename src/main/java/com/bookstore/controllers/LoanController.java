package com.bookstore.controllers;

import com.bookstore.dtos.LoanRecordDto;
import com.bookstore.models.LoanModel;
import com.bookstore.services.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_LIBRARIAN')")
    public ResponseEntity<List<LoanModel>> getLoans() {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getAllLoans());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_LIBRARIAN')")
    public ResponseEntity<LoanModel> getLoanById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getLoanById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_LIBRARIAN')")
    public ResponseEntity<LoanModel> save(@RequestBody LoanRecordDto loanRecordDto) {
        LoanModel response = loanService.saveLoan(loanRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_LIBRARIAN')")
    public ResponseEntity<LoanModel> update(@PathVariable UUID id, @RequestBody LoanRecordDto loanRecordDto) {
        LoanModel response = loanService.updateLoan(id, loanRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
