package com.sparta.board.user.exception;

import com.sparta.board.common.exception.BusinessException;
import com.sparta.board.common.exception.ErrorCode;

public class UserException extends BusinessException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
