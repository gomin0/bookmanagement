package com.bookmanagement.bookmanagement.controller;

import com.bookmanagement.bookmanagement.dto.book.BookRequest;
import com.bookmanagement.bookmanagement.dto.book.BookResponse;
import com.bookmanagement.bookmanagement.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 도서 등록 API
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid BookRequest request) {
        return ResponseEntity.ok(bookService.addBook(request));
    }

    // 모든 도서 조회 API
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // 특정 도서 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}