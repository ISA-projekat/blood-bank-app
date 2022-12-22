package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.dto.AppointmentReviewDto;
import com.bloodbank.bloodbankapp.enums.AppointmentSlotStatus;
import com.bloodbank.bloodbankapp.enums.AppointmentStatus;
import com.bloodbank.bloodbankapp.exception.NotFoundException;
import com.bloodbank.bloodbankapp.model.Appointment;
import com.bloodbank.bloodbankapp.model.Survey;
import com.bloodbank.bloodbankapp.model.User;
import com.bloodbank.bloodbankapp.repository.AppointmentRepository;
import com.bloodbank.bloodbankapp.utils.MailJetMailer;
import com.mailjet.client.errors.MailjetException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import com.bloodbank.bloodbankapp.enums.AppointmentStatus.*;

import java.util.Collections;
import java.util.List;

import static com.bloodbank.bloodbankapp.enums.AppointmentStatus.*;
import static com.bloodbank.bloodbankapp.enums.AppointmentSlotStatus.*;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final BloodBankService bloodBankService;

    private final UserService userService;

    private final SurveyService surveyService;

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public void review(AppointmentReviewDto appointmentReviewDto) {
        var appointment = appointmentRepository.findById(appointmentReviewDto.getId()).orElseThrow(() -> new NotFoundException("Appointment not found"));
        if (!appointment.getStatus().equals(SCHEDULED)) {
            throw new NotFoundException("Appointment has already been processed");
        }
        processReview(appointment, appointmentReviewDto);
        appointmentRepository.save(appointment);
    }

    private void processReview(Appointment appointemnt, AppointmentReviewDto appointmentReviewDto) {
        switch (appointmentReviewDto.getStatus()) {
            case CANCELED -> {
                appointemnt.setStatus(CANCELED);
                userService.penalise(appointemnt.getUser());
            }
            case FINISHED -> {
                appointemnt.updateDetails(appointmentReviewDto.getDescription());
                appointemnt.setStatus(FINISHED);
                bloodBankService.updateBloodStockAndEquipment(appointemnt.getAppointmentSlot().getBloodBank(), appointmentReviewDto.getBloodStock(), appointmentReviewDto.getEquipmentSetsUsed());
            }
            case NOT_ALLOWED -> appointemnt.setStatus(NOT_ALLOWED);
            default -> throw new NotFoundException("Appointment status not found");
        }
    }

    public List<Appointment> getAllByUser(Long userId) {
        return appointmentRepository.findAllByUserId(userId);
    }

    private Appointment findLatestUserAppointment(Long userId) {
        List<Appointment> appointments = getAllByUser(userId);
        Appointment latest = appointments.get(0);

        for(Appointment appointment : appointments)
            if(latest.getAppointmentSlot().getDateRange().dateIsAfter(appointment.getAppointmentSlot().getDateRange().getStart()) && (appointment.getStatus() == SCHEDULED || appointment.getStatus() == FINISHED))
                latest = appointment;

        return latest;
    }

    public Appointment schedule(Appointment appointment) throws MailjetException {
        User user = appointment.getUser();
        try {
            Survey survey = surveyService.getByUser(user.getId());
            if(getAllByUser(user.getId()).isEmpty()) {
                appointment.getAppointmentSlot().setStatus(TAKEN);
                MailJetMailer.SendScheduleAppointmentMail(user.getEmail());
                return appointmentRepository.save(appointment);
            }

            Appointment latest = findLatestUserAppointment(user.getId());
            if( !appointment.getAppointmentSlot().getDateRange().dateIsAfter(latest.getAppointmentSlot().getDateRange().getEnd().plusMonths(6)) ) {
                appointment.getAppointmentSlot().setStatus(TAKEN);
                MailJetMailer.SendScheduleAppointmentMail(user.getEmail());
                return appointmentRepository.save(appointment);
            }

            return null;
        }
        catch(NotFoundException e) {
            return null;
        }
    }

    public Appointment cancel(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException("No appointment found"));
        appointment.setStatus(CANCELED);
        appointment.setAppointmentSlot(null);
        userService.penalise(appointment.getUser());
        appointmentRepository.save(appointment);
        return appointment;
    }

    public List<Appointment> getByBloodBank(long id) {
        return appointmentRepository.findAllByAppointmentSlotBloodBankId(id);
    }

    public Appointment get(Long id){
        return appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException("No appointment found"));
    }
}
