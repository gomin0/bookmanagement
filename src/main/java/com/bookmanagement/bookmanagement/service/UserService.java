package com.bookmanagement.bookmanagement.service;

import com.bookmanagement.bookmanagement.dto.user.UserRequest;
import com.bookmanagement.bookmanagement.dto.user.UserResponse;
import com.bookmanagement.bookmanagement.entity.User;
import com.bookmanagement.bookmanagement.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse addUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("이미 등록된 이메일입니다: " + request.getEmail());
        }

        User user = request.toEntity();
        User savedUser = userRepository.save(user);

        return new UserResponse(savedUser);
    }
}
