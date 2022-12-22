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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PreUpdate;
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
    @PreAuthorize("hasRole('BLOOD_BANK_ADMIN') or hasRole('SYS_ADMIN') or hasRole('REGISTERED')")
    public List<Appointment> getAllByUser(@PathVariable("id") Long userId) {
        return appointmentService.getAllByUser(userId);
    }

    @PostMapping("/review")
    @PreAuthorize("hasRole('BLOOD_BANK_ADMIN')")
    public void review(@RequestBody AppointmentReviewDto appointmentReviewDto) {
        appointmentService.review(appointmentReviewDto);
    }

    @CrossOrigin
    @PostMapping("/schedule")
    @PreAuthorize("hasRole('REGISTERED')")
    public Appointment schedule(@RequestBody AppointmentDTO appointmentDTO) {
        User user = userService.getByUser(appointmentDTO.getUserId());
        AppointmentSlot appointmentSlot = appointmentSlotService.get(appointmentDTO.getAppointmentSlotId());
        appointmentSlotService.takeSlot(appointmentSlot.getId());
        Appointment appointment = Appointment.builder()
                .status(AppointmentStatus.SCHEDULED)
                .details(null)
                .appointmentSlot(appointmentSlot)
                .user(user)
                .build();

        return appointmentService.schedule(appointment) == null ? null : appointment;
    }

    @CrossOrigin
    @DeleteMapping("/cancel/{id}")
    @PreAuthorize("hasRole('REGISTERED')")
    public Appointment cancelAppointment(@PathVariable("id") Long id) {
        Appointment app = appointmentService.get(id);
        appointmentSlotService.cancelAppointment(app.getAppointmentSlot().getId());
        return appointmentService.cancel(id);
    }

    @GetMapping("/blood-bank/{id}")
    @PreAuthorize("hasRole('BLOOD_BANK_ADMIN') or hasRole('SYS_ADMIN') or hasRole('REGISTERED')")
    public List<Appointment> getByBloodBank(@PathVariable("id") long id){
        return appointmentService.getByBloodBank(id);
    }

}
