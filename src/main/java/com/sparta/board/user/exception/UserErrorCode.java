package com.sparta.board.user.exception;

import com.sparta.board.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements ErrorCode {
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST,"이미 존재하는 닉네임입니다"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST,"아이디를 다시 확인해주세요"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호를 다시 확인해주세요");

    private final HttpStatus httpStatus;
    private final String message;

    UserErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getCode() {
        return this.name();
    }
}
