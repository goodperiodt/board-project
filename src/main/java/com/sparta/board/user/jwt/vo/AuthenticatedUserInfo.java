package com.sparta.board.user.jwt.vo;

import com.sparta.board.user.domain.Role;

public record AuthenticatedUserInfo(String username, Role role) {
}
