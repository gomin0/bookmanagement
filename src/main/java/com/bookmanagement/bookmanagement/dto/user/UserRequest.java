package com.bookmanagement.bookmanagement.dto.user;

import com.bookmanagement.bookmanagement.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    @Builder
    public UserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .build();
    }
}