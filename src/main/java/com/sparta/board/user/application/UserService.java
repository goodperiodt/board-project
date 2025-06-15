package com.sparta.board.user.application;

import com.sparta.board.user.presentation.dto.request.SignUpRequest;

interface UserService {
    String signUp(SignUpRequest request);
}
