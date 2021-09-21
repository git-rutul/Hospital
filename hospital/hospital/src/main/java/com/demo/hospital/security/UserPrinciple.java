package com.demo.hospital.security;

import com.demo.hospital.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
public class UserPrinciple implements UserDetails {

    Integer id;

    String email;

    String password;

    GrantedAuthority grantedAuthority;

    public UserPrinciple(Integer id, String email, String password,GrantedAuthority grantedAuthority) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.grantedAuthority = grantedAuthority;
    }

    static UserPrinciple CreateUser(User user){
        return new UserPrinciple(user.getId(),user.getEmail(),user.getPassword(),new SimpleGrantedAuthority(user.getEmail()));
    }

    public static UserDetails create(User user) {
        return new UserPrinciple(user.getId(),user.getEmail(),user.getPassword(),new SimpleGrantedAuthority(user.getEmail()));
    }

    //Rutul method
    public static UserPrinciple create1(User user) {
        return new UserPrinciple(user.getId(),user.getEmail(),user.getPassword(),new SimpleGrantedAuthority(user.getEmail()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrinciple that = (UserPrinciple) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
