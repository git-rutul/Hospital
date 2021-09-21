package com.demo.hospital.security;


import com.demo.hospital.user.entity.User;
import com.demo.hospital.user.user_repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserDetailsService {

    final
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        userRepository.findById(username);
        User user=null;
        try {
            user=userRepository.findByEmail(username).get();
        }catch (Throwable e){
            e.printStackTrace();
        }
        return UserPrinciple.create(user);
    }

    public UserDetails loadUserByUserId(Integer id) throws UsernameNotFoundException{
        User user=userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found with id " + id));
        return UserPrinciple.CreateUser(user);
    }
}
