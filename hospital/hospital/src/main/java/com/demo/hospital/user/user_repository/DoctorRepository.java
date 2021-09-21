package com.demo.hospital.user.user_repository;

import com.demo.hospital.user.entity.Doctor;
import com.demo.hospital.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {

    Optional<Doctor> findByUser(User user);

}
