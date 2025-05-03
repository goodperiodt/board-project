package com.sparta.board.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "아이디 입력은 필수입니다.")
    private String email;

    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;
}
