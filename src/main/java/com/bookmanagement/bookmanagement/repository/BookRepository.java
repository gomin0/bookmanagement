package com.bookmanagement.bookmanagement.repository;

import com.bookmanagement.bookmanagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, Long id);

    @Query("SELECT b FROM Book b WHERE " +
            "(LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) AND :type = 'title') OR " +
            "(LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) AND :type = 'author')")
    List<Book> searchBooks(@Param("type") String type, @Param("keyword") String keyword);

    @Query("SELECT b FROM Book b JOIN b.tags t WHERE t IN :tags GROUP BY b HAVING COUNT(t) = :tagCount")
    List<Book> filterByTags(@Param("tags") List<String> tags, @Param("tagCount") long tagCount);
}