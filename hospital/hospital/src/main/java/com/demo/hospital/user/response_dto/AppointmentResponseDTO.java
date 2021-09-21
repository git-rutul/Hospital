package com.demo.hospital.user.response_dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentResponseDTO {

    private LocalDateTime appointmentTime;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private String address;

}
