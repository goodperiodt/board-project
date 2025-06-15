package com.sparta.board.user.domain;

import com.sparta.board.common.domain.BaseEntity;
import com.sparta.board.user.exception.UserErrorCode;
import com.sparta.board.user.exception.UserException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.sparta.board.user.config.SecurityConfig.passwordEncoder;

@Getter
@Entity
@Table(
        name = "t_users",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_USER_email",
                columnNames = "email"
        )
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    private User(
            String email,
            String encodedPassword,
            String nickname,
            String profileImageUrl,
            Role role
    ) {
        this.email = email;
        this.password = encodedPassword;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

    public static User create(
            String email,
            String encodedPassword,
            String nickname,
            String profileImageUrl,
            Role role
    ) {
        return new User(
                email,
                encodedPassword,
                nickname,
                profileImageUrl,
                role);
    }

    public boolean isSamePassword(String reqPassword) {
        if(!passwordEncoder().matches(reqPassword, password))
            throw new UserException(UserErrorCode.INVALID_PASSWORD);
        return true;
    }
}
