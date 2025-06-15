package com.sparta.board.user.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.board.user.domain.Role;
import com.sparta.board.user.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserDetailsImpl implements UserDetails {
    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public Role getRole() {
        return user.getRole();
    }

    public Long getId() {
        return user.getId();
    }

}
