package com.demo.hospital.user.request_dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class AddAppointmentReqDto {

    @NotNull
    Integer doctorId;

    @NotNull
    Integer userId;

    @NotBlank
    String appointmenDateTime;


}
