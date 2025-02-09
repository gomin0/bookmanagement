package com.bookmanagement.bookmanagement.controller;

import com.bookmanagement.bookmanagement.dto.book.BookRequest;
import com.bookmanagement.bookmanagement.dto.book.BookResponse;
import com.bookmanagement.bookmanagement.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<BookResponse>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,  // 기본값: title
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(bookService.getAllBooks(page, size, sortBy, direction));
    }

    // 특정 도서 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // 도서 수정 API
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @RequestBody @Valid BookRequest request
    ) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    // 도서 삭제 API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}