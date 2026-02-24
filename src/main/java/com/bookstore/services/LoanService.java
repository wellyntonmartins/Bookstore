package com.bookstore.services;

import com.bookstore.dtos.LoanRecordDto;
import com.bookstore.enums.LoanStatus;
import com.bookstore.exceptions.DataFormatWrongException;
import com.bookstore.models.LoanModel;
import com.bookstore.repositories.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LoanService {
    private LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<LoanModel> getAllLoans() {
        return loanRepository.findAll();
    }

    public LoanModel getLoanById(UUID id) {
        if (id == null) {
            throw new DataFormatWrongException("The provided UUID can't be empty or null.");
        }
        return loanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with UUID: '" + id + "' not found."));
    }

    public LoanModel saveLoan(LoanRecordDto loanRecordDto) {

    }

    private static List<String> checkLoanStatusOrder(LoanStatus atual_status, String method_type) {
        List<String> available_status = new ArrayList<>();
        switch (method_type) {
            case "save":
                break;
            case "update":

            default:
                throw new InternalError("Failed to check Loan status order: method_type '" + method_type + "' isn't a valid method type.");
        }
    }
}
