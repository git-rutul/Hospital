package com.demo.hospital.user.service;

import com.demo.hospital.user.entity.User;
import com.demo.hospital.user.user_repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Getter
@Setter
public class UserAuthentication {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    protected Integer getId(HttpServletRequest request) {
        if (request != null
                && request.getAttribute("id") != null) {
            return (Integer) request.getAttribute("id");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"error-internal-server-not-found");
//        return "";
    }

    protected User getUserFromUserId(Integer userId){
        Optional<User> user=userRepository.findById(userId);
        if(!user.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"error-internal-server-not-found");
        }
        return user.get();
    }

    protected User getUserFromAccessToken(HttpServletRequest request){
        Integer id=getId(request);
        Optional<User> user=userRepository.findById(id);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"error-internal-server-not-found");
        }
        return user.get();
    }

}
