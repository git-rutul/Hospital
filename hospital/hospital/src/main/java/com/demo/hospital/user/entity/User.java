package com.demo.hospital.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "is_email_virify")
    private Boolean isEmailVerify;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private UserRole userRole;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Appointment appointment;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Doctor doctor;

}
