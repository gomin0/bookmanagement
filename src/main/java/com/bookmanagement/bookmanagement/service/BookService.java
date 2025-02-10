package com.bookmanagement.bookmanagement.service;

import com.bookmanagement.bookmanagement.dto.book.BookRequest;
import com.bookmanagement.bookmanagement.dto.book.BookResponse;
import com.bookmanagement.bookmanagement.entity.Book;
import com.bookmanagement.bookmanagement.repository.BookRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    // 도서 추가 (도서 목록 캐시 무효화)
    @CacheEvict(value = "books", allEntries = true)
    public BookResponse addBook(BookRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new EntityExistsException("이미 존재하는 ISBN입니다: " + request.getIsbn());
        }

        Book book = request.toEntity();
        Book savedBook = bookRepository.save(book);
        return new BookResponse(savedBook);
    }

    // 도서 목록 조회 (캐싱 적용)
    @Transactional(readOnly = true)
    @Cacheable(value = "books")
    public Page<BookResponse> getAllBooks(int page, int size, String sortBy, String direction) {
        if (!sortBy.equals("title") && !sortBy.equals("createdAt")) {
            throw new IllegalArgumentException("sortBy는 'title' 또는 'createdAt'만 가능합니다.");
        }

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Book> books = bookRepository.findAll(pageable);

        return books.map(BookResponse::new);
    }

    // 특정 도서 조회 (캐싱 적용)
    @Transactional(readOnly = true)
    @Cacheable(value = "book", key = "#id")
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 도서를 찾을 수 없습니다: " + id));
        return new BookResponse(book);
    }

    // 도서 수정 (도서 목록 캐시 무효화 + 개별 도서 캐시 업데이트)
    @CacheEvict(value = "books", allEntries = true)
    @CachePut(value = "book", key = "#id")
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 도서를 찾을 수 없습니다: " + id));

        if (bookRepository.existsByIsbnAndIdNot(request.getIsbn(), id)) {
            throw new EntityExistsException("이미 존재하는 ISBN입니다: " + request.getIsbn());
        }

        book.update(request.getIsbn(), request.getTitle(), request.getAuthor());
        book.updateTags(request.getTags());

        return new BookResponse(book);
    }

    // 도서 삭제 (도서 목록 + 개별 도서 캐시 무효화)
    @Caching(evict = {
            @CacheEvict(value = "books", allEntries = true), // 도서 목록 캐시 삭제
            @CacheEvict(value = "book", key = "#id") // 특정 도서 캐시 삭제
    })
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("해당 ID의 도서를 찾을 수 없습니다: " + id);
        }
        bookRepository.deleteById(id);
    }

    // 도서 검색 (캐싱 적용)
    @Transactional(readOnly = true)
    @Cacheable(value = "searchBooks", key = "#type + '-' + #keyword")
    public List<BookResponse> searchBooks(String type, String keyword) {
        if (!type.equals("title") && !type.equals("author")) {
            throw new IllegalArgumentException("검색 타입은 'title' 또는 'author'만 가능합니다.");
        }

        List<Book> books = bookRepository.searchBooks(type, keyword);
        return books.stream().map(BookResponse::new).collect(Collectors.toList());
    }

    // 태그 기반 도서 필터링 (5분 TTL 적용)
    @Transactional(readOnly = true)
    @Cacheable(value = "filterBooksByTags", key = "#tags.toString()")
    public List<BookResponse> filterBooksByTags(List<String> tags) {
        List<Book> books = bookRepository.filterByTags(tags, tags.size());
        return books.stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
}