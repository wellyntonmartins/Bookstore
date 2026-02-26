package com.bookstore.services;

import com.bookstore.dtos.LoanRecordDto;
import com.bookstore.utils.Methods;
import com.bookstore.enums.LoanStatus;
import com.bookstore.exceptions.DataFormatWrongException;
import com.bookstore.models.BookModel;
import com.bookstore.models.LoanModel;
import com.bookstore.models.StudentModel;
import com.bookstore.repositories.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bookstore.utils.Methods.parseDateToLocalDate;

@Service
public class LoanService {
    private LoanRepository loanRepository;
    private BookService bookService;
    private StudentService studentService;

    public LoanService(LoanRepository loanRepository,  BookService bookService,  StudentService studentService) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
        this.studentService = studentService;
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
        BookModel book = bookService.getBookById(loanRecordDto.bookId());
        StudentModel student = studentService.getStudentById(loanRecordDto.studentId());

        if (!StringUtils.hasText(loanRecordDto.date()) || !StringUtils.hasText(loanRecordDto.due_date())) {
            throw new DataFormatWrongException("The date or/and due date cannot be empty.");
        }

        LocalDate date = parseDateToLocalDate(loanRecordDto.date());
        LocalDate due_date = parseDateToLocalDate(loanRecordDto.due_date());

        List<LoanStatus> available_status = checkLoanStatusOrder(loanRecordDto.status(), "save");

        if (!available_status.contains(loanRecordDto.status())) {
            throw new DataFormatWrongException("This loan status isn't in order (IN_TIMEFRAME -> OVERDUE -> RETURNED). Please, try again.");
        }

        LoanModel newLoan =  new LoanModel();
        newLoan.setBook(book);
        newLoan.setStudent(student);
        newLoan.setDate(date);
        newLoan.setDue_date(due_date);
        newLoan.setStatus(loanRecordDto.status());

        return loanRepository.save(newLoan);
    }

    public LoanModel updateLoan(UUID id, LoanRecordDto loanRecordDto) {
        BookModel book = bookService.getBookById(loanRecordDto.bookId());
        StudentModel student = studentService.getStudentById(loanRecordDto.studentId());

        if (!StringUtils.hasText(loanRecordDto.date()) || !StringUtils.hasText(loanRecordDto.due_date())) {
            throw new DataFormatWrongException("The date or/and due date cannot be empty.");
        }

        LocalDate date = parseDateToLocalDate(loanRecordDto.date());
        LocalDate due_date = parseDateToLocalDate(loanRecordDto.due_date());

        LoanModel loanToUpdate = getLoanById(id);

        List<LoanStatus> available_status = checkLoanStatusOrder(loanRecordDto.status(), "update");

        if (!available_status.contains(loanRecordDto.status())) {
            throw new DataFormatWrongException("This loan status isn't in order (IN_TIMEFRAME -> OVERDUE -> RETURNED). Please, try again.");
        }

        loanToUpdate.setBook(book);
        loanToUpdate.setStudent(student);
        loanToUpdate.setDate(date);
        loanToUpdate.setDue_date(due_date);
        loanToUpdate.setStatus(loanRecordDto.status());

        return loanRepository.save(loanToUpdate);
    }

    private static List<LoanStatus> checkLoanStatusOrder(LoanStatus atual_status, String method_type) {
        List<LoanStatus> available_status = new ArrayList<>();
        switch (method_type) {
            case "save":
                if (!atual_status.equals(LoanStatus.IN_TIMEFRAME)) {
                    throw new DataFormatWrongException("The first status of loan must be 'IN_TIMEFRAME'.");
                }

                available_status.add(atual_status);
                return available_status;
            case "update":
                switch (atual_status) {
                    case IN_TIMEFRAME:
                        available_status.add(LoanStatus.OVERDUE);
                        return available_status;
                    case OVERDUE:
                        available_status.add(LoanStatus.RETURNED);
                        return available_status;
                    case RETURNED:
                        available_status.add(LoanStatus.IN_TIMEFRAME);
                        available_status.add(LoanStatus.OVERDUE);
                        return available_status;
                }
            default:
                throw new InternalError("Failed to check Loan status order: method_type '" + method_type + "' isn't a valid method type.");
        }
    }
}
