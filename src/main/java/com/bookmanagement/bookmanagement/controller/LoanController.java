package com.bookmanagement.bookmanagement.controller;

import com.bookmanagement.bookmanagement.dto.loan.LoanRequest;
import com.bookmanagement.bookmanagement.dto.loan.LoanResponse;
import com.bookmanagement.bookmanagement.dto.loan.LoanStatusResponse;
import com.bookmanagement.bookmanagement.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    // 대출 등록 API
    @PostMapping
    public ResponseEntity<LoanResponse> loanBook(@RequestBody @Valid LoanRequest request) {
        return ResponseEntity.ok(loanService.loanBook(request));
    }

    // 대출 상태 확인 API
    @GetMapping("/{bookId}")
    public ResponseEntity<LoanStatusResponse> getBookLoanStatus(@PathVariable Long bookId) {
        return ResponseEntity.ok(loanService.getBookLoanStatus(bookId));
    }

    // 도서 반납 API
    @PostMapping("/{bookId}/return")
    public ResponseEntity<Void> returnBook(@PathVariable Long bookId) {
        loanService.returnBook(bookId);
        return ResponseEntity.ok().build();
    }
}