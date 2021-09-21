package com.demo.hospital.user.service;

import com.demo.hospital.config.MapperFacadeConfig;
import com.demo.hospital.user.entity.Appointment;
import com.demo.hospital.user.entity.Doctor;
import com.demo.hospital.user.entity.User;
import com.demo.hospital.user.request_dto.AddAppointmentReqDto;
import com.demo.hospital.user.response_dto.AppointmentResponseDTO;
import com.demo.hospital.user.user_repository.AppointmentRepository;
import com.demo.hospital.user.user_repository.DoctorRepository;
import com.demo.hospital.user.user_repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends UserAuthentication {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MapperFacadeConfig mapperFacadeConfig;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    DoctorRepository doctorRepository;


    public void addAppointment(@Valid AddAppointmentReqDto addAppointmentReqDto) {
        User docUser = getUserFromUserId(addAppointmentReqDto.getUserId());
        User patientUser = getUserFromUserId(addAppointmentReqDto.getUserId());

        Optional<Doctor> doctor = doctorRepository.findByUser(docUser);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime appointmentTime = LocalDateTime.parse(addAppointmentReqDto.getAppointmenDateTime(), formatter);

        Optional<Appointment> appointment = appointmentRepository.findByAppointmentTimeAndDoctor(appointmentTime, doctor.get());
        if (appointment.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "error-already-booked");
        }

        Appointment apoAppointment1 = new Appointment();
        apoAppointment1.setAppointmentTime(appointmentTime);
        apoAppointment1.setDoctor(doctor.get());
        apoAppointment1.setUser(patientUser);
        appointmentRepository.save(apoAppointment1);

    }

    public void editAppointment(@Valid @NotBlank Integer appointmentId, @Valid @NotNull LocalDateTime updatedTime) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()) {
            appointment.get().setAppointmentTime(updatedTime);
            appointmentRepository.save(appointment.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error-appointment-not-found");
        }
    }

    public AppointmentResponseDTO getAppointment(Integer appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()) {
            AppointmentResponseDTO appointmentResponseDTO=mapperFacadeConfig.mapper.map(appointment.get().getUser(),AppointmentResponseDTO.class);
            appointmentResponseDTO.setAppointmentTime(appointment.get().getAppointmentTime());
            return appointmentResponseDTO;
        }
        return new AppointmentResponseDTO();
    }

    public Optional<List<Appointment>> listAppointment(@Valid @NotNull Integer docId) {
        User docUser = getUserFromUserId(docId);

        Doctor doctor = docUser.getDoctor();

        Optional<List<Appointment>> appointmentList = appointmentRepository.findByDoctor(doctor);
        List<AppointmentResponseDTO> appointmentResponseDTOList = new ArrayList<>();
        if (appointmentList.isPresent()) {
            for (Appointment appointment : appointmentList.get()) {
                AppointmentResponseDTO appointmentResponseDTO=mapperFacadeConfig.mapper.map(appointment.getUser(),AppointmentResponseDTO.class);
                appointmentResponseDTO.setAppointmentTime(appointment.getAppointmentTime());
                appointmentResponseDTOList.add(appointmentResponseDTO);

            }
        }
        return appointmentList;
    }
}