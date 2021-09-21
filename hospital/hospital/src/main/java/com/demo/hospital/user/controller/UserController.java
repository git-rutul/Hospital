package com.demo.hospital.user.controller;

import com.demo.hospital.base.ResponseHandler;
import com.demo.hospital.base.Urls;
import com.demo.hospital.user.entity.Appointment;
import com.demo.hospital.user.entity.User;
import com.demo.hospital.user.request_dto.AddAppointmentReqDto;
import com.demo.hospital.user.response_dto.AppointmentResponseDTO;
import com.demo.hospital.user.service.UserService;
import com.demo.hospital.user.user_repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Urls.BASE_URL)
@Validated
public class UserController extends ResponseHandler {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = Urls.VERSION_V1 + Urls.ADD_APPOINTMENT)
    ResponseEntity<?> addAppointment(HttpServletRequest httpServletRequest, @Valid @RequestBody AddAppointmentReqDto addAppointmentReqDto) {
        User user =getUserFromAccessToken(httpServletRequest);
        userService.addAppointment(addAppointmentReqDto);
        return okResponse(HttpStatus.OK, "user-sign-up");
    }

    @PutMapping(value = Urls.VERSION_V1 + Urls.EDIT_APPOINTMENT)
    ResponseEntity<?> editAppointment(HttpServletRequest httpServletRequest,@Valid @RequestParam @NotNull Integer appointmentId,
    @Valid @RequestParam @NotNull LocalDateTime updatedTime) {
        User user =getUserFromAccessToken(httpServletRequest);
        userService.editAppointment(appointmentId,updatedTime);
        return okResponse(HttpStatus.OK, "user-sign-up");
    }

    @GetMapping(value = Urls.VERSION_V1 + Urls.GET_APPOINTMENT)
    ResponseEntity<?> getAppointment(HttpServletRequest httpServletRequest,@Valid @RequestParam @NotNull Integer appointmentId) {
        User user =getUserFromAccessToken(httpServletRequest);
        AppointmentResponseDTO appointmentResponseDTO=userService.getAppointment(appointmentId);
        return okResponse(appointmentResponseDTO,HttpStatus.OK, "user-sign-up");
    }

    @GetMapping(value = Urls.VERSION_V1 + Urls.LIST_APPOINTMENT)
    ResponseEntity<?> listAppointment(HttpServletRequest httpServletRequest,@Valid @RequestParam @NotNull Integer docId) {
        User user =getUserFromAccessToken(httpServletRequest);
        Optional<List<Appointment>> appointmentList= userService.listAppointment(docId);
        return okResponse(appointmentList,HttpStatus.OK, "user-sign-up");
    }

}
