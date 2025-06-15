package com.sparta.board.user.domain;

import lombok.Getter;

@Getter
public enum Role {
    MASTER("마스터"),
    MEMBER("회원");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}


