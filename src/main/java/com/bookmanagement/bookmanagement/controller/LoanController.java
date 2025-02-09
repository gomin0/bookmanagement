package com.bookmanagement.bookmanagement.controller;

import com.bookmanagement.bookmanagement.dto.loan.LoanRequest;
import com.bookmanagement.bookmanagement.dto.loan.LoanResponse;
import com.bookmanagement.bookmanagement.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}