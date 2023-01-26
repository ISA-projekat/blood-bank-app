package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.dto.AppointmentCalendarItemDTO;
import com.bloodbank.bloodbankapp.dto.AppointmentDTO;
import com.bloodbank.bloodbankapp.dto.AppointmentPreviewDto;
import com.bloodbank.bloodbankapp.dto.AppointmentReviewDto;
import com.bloodbank.bloodbankapp.enums.AppointmentStatus;
import com.bloodbank.bloodbankapp.model.Appointment;
import com.bloodbank.bloodbankapp.model.AppointmentSlot;
import com.bloodbank.bloodbankapp.model.User;
import com.bloodbank.bloodbankapp.service.AppointmentService;
import com.bloodbank.bloodbankapp.service.AppointmentSlotService;
import com.bloodbank.bloodbankapp.service.UserService;
import com.bloodbank.bloodbankapp.utils.MailJetMailer;
import com.mailjet.client.errors.MailjetException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    private final AppointmentSlotService appointmentSlotService;

    private final UserService userService;

    @Autowired
    private MailJetMailer mailJetMailer;

    @GetMapping
    public List<Appointment> getAll() {
        return appointmentService.getAll();
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('BLOOD_BANK_ADMIN') or hasRole('SYS_ADMIN') or hasRole('REGISTERED')")
    public List<AppointmentPreviewDto> getAllByUser(@PathVariable("id") Long userId) {
        return appointmentService.getAllByUser(userId);
    }

    @PostMapping("/review")
//    @PreAuthorize("hasRole('BLOOD_BANK_ADMIN')")
    public void review(@RequestBody AppointmentReviewDto appointmentReviewDto) {
        appointmentService.review(appointmentReviewDto);
    }

    @CrossOrigin
    @PostMapping("/schedule")
    @PreAuthorize("hasRole('REGISTERED')")
    public Appointment schedule(@RequestBody AppointmentDTO appointmentDTO) {
        User user = userService.getByUser(appointmentDTO.getUserId());
        AppointmentSlot appointmentSlot = appointmentSlotService.get(appointmentDTO.getAppointmentSlotId());
        try {
            return appointmentService.schedule(appointmentSlot, user);
        } catch (MailjetException e) {
            System.out.println("Mailjet exception, couldn't schedule because of it");
            return null;
        }
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
    public List<Appointment> getByBloodBank(@PathVariable("id") long id) {
        return appointmentService.getByBloodBank(id);
    }

    @GetMapping("/user/{id}/{status}")
    @PreAuthorize("hasRole('BLOOD_BANK_ADMIN') or hasRole('SYS_ADMIN') or hasRole('REGISTERED')")
    public List<Appointment> findAllAppointmentsByStatusByUserId(@PathVariable("status") AppointmentStatus status, @PathVariable("id") Long userId) {
        return appointmentService.findAllAppointmentsByStatusByUserId(status, userId);
    }

    @GetMapping("/blood-bank/{id}/{status}")
    public List<Appointment> findAllAppointmentsByStatusByBloodBankId(@PathVariable("status") AppointmentStatus status, @PathVariable("id") Long bloodBankId) {
        return appointmentService.findAllAppointmentsByStatusByBloodBankId(status, bloodBankId);
    }

    @CrossOrigin
    @GetMapping("/by-blood-bank/{id}")
    public List<AppointmentCalendarItemDTO> findAllByBloodBank(@PathVariable("id") Long bloodBankId) {
        return appointmentService.findAllByBloodBank(bloodBankId);
    }

    @CrossOrigin
    @PostMapping("/generate-qr")
    public void generateQrForAppointment() throws MessagingException {
        appointmentService.generateQRCodeForAppointment();
        Appointment appointment = appointmentService.getLastScheduledAppointment();
        String toEmail = appointment.getUser().getEmail();
        String id = appointment.getId().toString();
        mailJetMailer.sendQRReservation(toEmail,
                "Thank you for being loyal to our blood bank!",
                "Appointment Reservation",
                "static/confirmation_"+id+"_QR.png");
    }
    
    @GetMapping("/user/finished")
    @PreAuthorize("hasRole('BLOOD_BANK_ADMIN') or hasRole('SYS_ADMIN') or hasRole('REGISTERED')")
    public Page<Appointment> findFinishedAppointmentsForUser(Long id, Pageable page) {
        return appointmentService.findFinishedByUser(id, page);
    }

}
