package com.hotelmanagement.security;

import com.hotelmanagement.entity.HotelSoftUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private String userId;
    private String username;
    private String password;
    private String userTypeId;
    private String hotelId;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String userId, String username, String password, String userTypeId, String hotelId, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userTypeId = userTypeId;
        this.hotelId = hotelId;
        this.authorities = authorities;
    }

    public static UserPrincipal create(HotelSoftUser user) {
        List<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_USER")
        );

        return new UserPrincipal(
            user.getUserId(),
            user.getUserName(),
            user.getPassword(),
            user.getUserTypeId(),
            user.getHotelId(),
            authorities
        );
    }

    public String getUserId() {
        return userId;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public String getHotelId() {
        return hotelId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
