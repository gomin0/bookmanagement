package com.bookmanagement.bookmanagement.dto.loan;

import lombok.Getter;

@Getter
public class LoanStatusResponse {
    private boolean available;
    private String message;

    public LoanStatusResponse(boolean available) {
        this.available = available;
        this.message = available ? "대출 가능" : "대출 중";
    }
}