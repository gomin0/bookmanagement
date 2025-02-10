package com.bookmanagement.bookmanagement.service;

import com.bookmanagement.bookmanagement.dto.loan.LoanRequest;
import com.bookmanagement.bookmanagement.dto.loan.LoanResponse;
import com.bookmanagement.bookmanagement.dto.loan.LoanStatusResponse;
import com.bookmanagement.bookmanagement.entity.Book;
import com.bookmanagement.bookmanagement.entity.Loan;
import com.bookmanagement.bookmanagement.entity.User;
import com.bookmanagement.bookmanagement.repository.BookRepository;
import com.bookmanagement.bookmanagement.repository.LoanRepository;
import com.bookmanagement.bookmanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    // 도서 대출 (대출 상태 캐시 무효화)
    @CacheEvict(value = "loanStatus", key = "#request.bookId")
    public LoanResponse loanBook(LoanRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + request.getUserId()));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("도서를 찾을 수 없습니다: " + request.getBookId()));

        if (!book.isAvailable()) {
            throw new IllegalStateException("이미 대출 중인 도서입니다.");
        }

        Loan loan = request.toEntity(user, book);

        Loan savedLoan = loanRepository.save(loan);
        return new LoanResponse(savedLoan);
    }

    // 도서 대출 상태 조회 (캐싱 적용)
    @Transactional(readOnly = true)
    @Cacheable(value = "loanStatus", key = "#bookId")
    public LoanStatusResponse getBookLoanStatus(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 도서를 찾을 수 없습니다: " + bookId));

        return new LoanStatusResponse(book.isAvailable());
    }

    // 도서 반납 (대출 상태 캐시 무효화)
    @CacheEvict(value = "loanStatus", key = "#bookId")
    public void returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 도서를 찾을 수 없습니다: " + bookId));

        if (book.isAvailable()) {
            throw new IllegalStateException("이 도서는 현재 대출되지 않았습니다.");
        }

        book.setAvailable(true);
    }
}
