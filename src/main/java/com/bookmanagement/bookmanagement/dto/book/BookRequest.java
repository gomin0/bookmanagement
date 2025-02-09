package com.bookmanagement.bookmanagement.dto.book;

import com.bookmanagement.bookmanagement.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookRequest {
    @NotBlank(message = "ISBN은 필수 입력 항목입니다.")
    @Size(min = 10, max = 13, message = "ISBN은 10~13자리여야 합니다.")
    private String isbn;

    @NotBlank(message = "책 제목은 필수 입력 항목입니다.")
    private String title;

    @NotBlank(message = "저자는 필수 입력 항목입니다.")
    private String author;

    @Builder
    public BookRequest(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public Book toEntity() {
        return Book.builder()
                .isbn(this.isbn)
                .title(this.title)
                .author(this.author)
                .available(true)
                .build();
    }
}