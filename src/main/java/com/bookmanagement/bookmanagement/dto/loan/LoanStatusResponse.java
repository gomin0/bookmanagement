package com.bookmanagement.bookmanagement.dto.loan;

import lombok.Getter;

@Getter
public class LoanStatusResponse {
    private boolean loaned;
    private String message;

    public LoanStatusResponse(boolean loaned) {
        this.loaned = loaned;
        this.message = loaned ? "대출 중" : "대출 가능";
    }
}