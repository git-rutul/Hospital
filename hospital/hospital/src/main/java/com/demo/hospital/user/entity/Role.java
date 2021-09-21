package com.demo.hospital.user.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "role")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    String roleName;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    List<UserRole> userRole;

}
