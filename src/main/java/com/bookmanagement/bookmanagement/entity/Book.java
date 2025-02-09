package com.bookmanagement.bookmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private boolean available = true;  // 대출 가능 여부

    @ElementCollection
    @CollectionTable(name = "book_tags", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>(); // 태그 기본값 설정;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void update(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public void updateTags(Set<String> newTags) {
        if (newTags == null) {
            newTags = new HashSet<>();
        }

        // 기존 태그와 새로운 태그를 비교 (순서 무시) -> 다른 경우에만 업데이트
        if (!this.tags.equals(newTags)) {
            this.tags.clear();
            this.tags.addAll(newTags);
        }
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}