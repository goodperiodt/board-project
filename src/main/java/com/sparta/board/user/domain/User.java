package com.sparta.board.user.domain;

import com.sparta.board.common.domain.BaseEntity;
import lombok.Getter;

@Getter
public class User extends BaseEntity {
    private Long id;
    private String account;
    private String password;
    private String nickName;
    private String profileImageUrl;
    private Role role;
}