package com.bookmanagement.bookmanagement.repository;

import com.bookmanagement.bookmanagement.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}