package com.demo.hospital.user.user_repository;

import com.demo.hospital.user.entity.Role;
import com.demo.hospital.user.entity.User;
import com.demo.hospital.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {

    Optional<UserRole> findByUser(User user);

    Optional<UserRole> findByUserAndRole(User user, Role role);
}
