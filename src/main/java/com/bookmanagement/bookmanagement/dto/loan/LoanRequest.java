package com.bookmanagement.bookmanagement.dto.loan;

import com.bookmanagement.bookmanagement.entity.Book;
import com.bookmanagement.bookmanagement.entity.Loan;
import com.bookmanagement.bookmanagement.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class LoanRequest {
    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotNull(message = "도서 ID는 필수입니다.")
    private Long bookId;

    public Loan toEntity(User user, Book book) {
        book.setAvailable(false);
        return Loan.builder()
                .user(user)
                .book(book)
                .loanDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(14)) // 기본 반납 기한 2주
                .build();
    }
}