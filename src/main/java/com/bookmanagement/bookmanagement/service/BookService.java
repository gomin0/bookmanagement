package com.bookmanagement.bookmanagement.service;

import com.bookmanagement.bookmanagement.dto.book.BookRequest;
import com.bookmanagement.bookmanagement.dto.book.BookResponse;
import com.bookmanagement.bookmanagement.entity.Book;
import com.bookmanagement.bookmanagement.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<BookResponse> getAllBooks(int page, int size, String sortBy, String direction) {
        if (!sortBy.equals("title") && !sortBy.equals("createdAt")) {
            throw new IllegalArgumentException("sortBy는 'title' 또는 'createdAt'만 가능합니다.");
        }

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Book> books = bookRepository.findAll(pageable);

        return books.map(BookResponse::new);
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 도서를 찾을 수 없습니다: " + id));
        return new BookResponse(book);
    }

    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 도서를 찾을 수 없습니다: " + id));

        book.update(request.getIsbn(), request.getTitle(), request.getAuthor());

        return new BookResponse(book);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("해당 ID의 도서를 찾을 수 없습니다: " + id);
        }
        bookRepository.deleteById(id);
    }
}