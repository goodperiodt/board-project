package com.sparta.board.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GlobalErrorCode implements ErrorCode {
    NOT_EXIST_ENDPOINT(HttpStatus.NOT_FOUND, "요청에 해당하는 endpoint 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    GlobalErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getCode() {
        return this.name();
    }
}
