package com.sparta.board.user.exception;

import com.sparta.board.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements ErrorCode {
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 계정입니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST,"이미 존재하는 닉네임입니다");
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
