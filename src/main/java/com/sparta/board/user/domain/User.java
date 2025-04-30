package com.sparta.board.user.domain;

import com.sparta.board.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "t_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    private User(
            String account,
            String encodedPassword,
            String nickName,
            String profileImageUrl,
            Role role
    ) {
        this.account = account;
        this.password = encodedPassword;
        this.nickName = nickName;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

    public static User create(
            String account,
            String encodedPassword,
            String nickName,
            String profileImageUrl,
            Role role
    ) {
        return new User(
                account,
                encodedPassword,
                nickName,
                profileImageUrl,
                role);
    }
}