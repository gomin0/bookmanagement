package com.bookmanagement.bookmanagement.dto.book;

import com.bookmanagement.bookmanagement.entity.Book;
import lombok.Getter;

import java.util.Set;

@Getter
public class BookResponse {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private boolean available;
    private Set<String> tags;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.available = book.isAvailable();
        this.tags = book.getTags();
    }
}