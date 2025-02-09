package com.bookmanagement.bookmanagement.dto.loan;

import com.bookmanagement.bookmanagement.entity.Loan;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LoanResponse {
    private Long loanId;
    private Long userId;
    private Long bookId;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public LoanResponse(Loan loan) {
        this.loanId = loan.getId();
        this.userId = loan.getUser().getId();
        this.bookId = loan.getBook().getId();
        this.loanDate = loan.getLoanDate();
        this.returnDate = loan.getReturnDate();
    }
}