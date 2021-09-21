package com.demo.hospital.user.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "user_role")
public class UserRole {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(referencedColumnName = "id",name = "user_id")
    User user;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id",name = "role_id")
    Role role;

}
