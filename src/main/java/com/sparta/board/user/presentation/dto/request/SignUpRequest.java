package com.sparta.board.user.presentation.dto.request;

import com.sparta.board.user.domain.Role;
import com.sparta.board.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRequest {
    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;

    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickname;

    private Role role;

    @NotBlank(message = "프로필 이미지는 필수입니다.")
    private String profileImageUrl;

    public User toEntity(String encodedPassword) {
        return User.create(
                email,
                encodedPassword,
                nickname,
                profileImageUrl,
                role
        );
    }
}
