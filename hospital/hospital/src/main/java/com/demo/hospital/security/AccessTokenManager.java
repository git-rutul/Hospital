package com.demo.hospital.security;

import com.demo.hospital.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class AccessTokenManager {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public String accessToken(String email, String passWord){

        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,passWord));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken=jwtTokenProvider.generateToken(authentication);

        return accessToken;
    }

    public String accessToken1(User user){

        Authentication auth = new UsernamePasswordAuthenticationToken(
                UserPrinciple.create1(user), null, Arrays.asList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String accessToken=jwtTokenProvider.generateToken(auth);

        return accessToken;
    }

}
