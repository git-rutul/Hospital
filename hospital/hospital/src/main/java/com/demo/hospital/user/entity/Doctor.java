package com.demo.hospital.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "specification")
    String specification;

    @Column(name = "available_to_time")
    LocalTime availablityToTime;

    @Column(name = "available_from_time")
    LocalTime availablityFromTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Appointment> appointment;

}
