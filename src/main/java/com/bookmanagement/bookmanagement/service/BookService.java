package com.bookmanagement.bookmanagement.service;

import com.bookmanagement.bookmanagement.dto.book.BookRequest;
import com.bookmanagement.bookmanagement.dto.book.BookResponse;
import com.bookmanagement.bookmanagement.entity.Book;
import com.bookmanagement.bookmanagement.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BookResponse addBook(BookRequest request) {
        Book book = request.toEntity();
        Book savedBook = bookRepository.save(book);
        return new BookResponse(savedBook);
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 도서를 찾을 수 없습니다: " + id));
        return new BookResponse(book);
    }
}