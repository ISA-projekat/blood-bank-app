package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.dto.AppointmentReviewDto;
import com.bloodbank.bloodbankapp.model.Appointment;
import com.bloodbank.bloodbankapp.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

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
}
