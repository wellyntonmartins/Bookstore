package com.bookstore.repositories;

import com.bookstore.models.LoanModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanRepository extends JpaRepository<LoanModel, UUID> {

}
