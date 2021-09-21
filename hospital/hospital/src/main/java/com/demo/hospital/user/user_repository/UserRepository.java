package com.demo.hospital.user.user_repository;


import com.demo.hospital.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmailAndIsActive(String email, boolean isActive);

    Optional<User> findByPhoneNumberAndIsActive(String email, boolean isActive);

    Optional<User> findByEmail(String email);
}
