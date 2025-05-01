package com.sparta.board.user.application;

import com.sparta.board.user.presentation.dto.request.SignUpRequest;
import jakarta.validation.Valid;

interface UserService {
    String signUp(SignUpRequest request);
}
