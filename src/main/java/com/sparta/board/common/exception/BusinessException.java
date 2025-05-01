package com.sparta.board.common.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
