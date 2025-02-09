package com.bookmanagement.bookmanagement.repository;

import com.bookmanagement.bookmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}