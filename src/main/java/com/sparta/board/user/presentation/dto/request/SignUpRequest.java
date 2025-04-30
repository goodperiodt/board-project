package com.sparta.board.user.presentation.dto.request;

import com.sparta.board.user.domain.Role;
import com.sparta.board.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequest {
    @NotBlank(message = "아이디 입력은 필수입니다.")
    private String account;

    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;

    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickName;

    private String masterCode;

    private String profileImageUrl;

    public String account() {
        return account;
    }

    public String nickName() {
        return nickName;
    }

    public String password() {
        return password;
    }

    public String getProfileImageUrlOrDefault() {
        return this.profileImageUrl = (profileImageUrl!=null)? profileImageUrl : "image.png";
    }

    public boolean isMaster() {
        return masterCode.equals("1234");
    }

    public Role assignRole() {
        return isMaster()? Role.MASTER:Role.MEMBER;
    }

    public User toEntity(String encodedPassword) {
        return User.create(
                account,
                encodedPassword,
                nickName,
                getProfileImageUrlOrDefault(),
                assignRole()
        );
    }
}
