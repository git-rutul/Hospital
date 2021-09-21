package com.demo.hospital.user.user_repository;

import com.demo.hospital.user.entity.Appointment;
import com.demo.hospital.user.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    Optional<Appointment> findByAppointmentTimeAndDoctor(LocalDateTime loaLocalDateTime, Doctor doctor);

    Optional<List<Appointment>> findByDoctor(Doctor doctor);
}
