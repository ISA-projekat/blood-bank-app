package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.dto.AppointmentDTO;
import com.bloodbank.bloodbankapp.dto.AppointmentReviewDto;
import com.bloodbank.bloodbankapp.enums.AppointmentStatus;
import com.bloodbank.bloodbankapp.model.Appointment;
import com.bloodbank.bloodbankapp.model.AppointmentSlot;
import com.bloodbank.bloodbankapp.model.User;
import com.bloodbank.bloodbankapp.service.AppointmentService;
import com.bloodbank.bloodbankapp.service.AppointmentSlotService;
import com.bloodbank.bloodbankapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    private final AppointmentSlotService appointmentSlotService;

    private final UserService userService;

    @GetMapping
    public List<Appointment> getAll() {
        return appointmentService.getAll();
    }

    @GetMapping("/user/{id}")
    public List<Appointment> getAllByUser(@PathVariable("id") Long userId) {
        return appointmentService.getAllByUser(userId);
    }

    @PostMapping("/review")
    public void review(@RequestBody AppointmentReviewDto appointmentReviewDto) {
        appointmentService.review(appointmentReviewDto);
    }

    @CrossOrigin
    @PostMapping("/schedule")
    public Appointment schedule(@RequestBody AppointmentDTO appointmentDTO) {
        User user = userService.getByUser(appointmentDTO.getUserId());
        AppointmentSlot appointmentSlot = appointmentSlotService.get(appointmentDTO.getAppointmentSlotId());
        Appointment appointment = Appointment.builder()
                .status(AppointmentStatus.SCHEDULED)
                .details(null)
                .appointmentSlot(appointmentSlot)
                .user(user)
                .build();

        return appointmentService.schedule(appointment);
    }

    @CrossOrigin
    @DeleteMapping("/cancel")
    public Appointment cancelAppointment(@RequestBody Appointment appointment) {
        return appointmentService.cancel(appointment);
    }

}
