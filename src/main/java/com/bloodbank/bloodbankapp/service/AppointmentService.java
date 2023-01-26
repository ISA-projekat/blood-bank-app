package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.dto.AppointmentCalendarItemDTO;
import com.bloodbank.bloodbankapp.dto.AppointmentPreviewDto;
import com.bloodbank.bloodbankapp.dto.AppointmentReviewDto;
import com.bloodbank.bloodbankapp.enums.AppointmentSlotStatus;
import com.bloodbank.bloodbankapp.enums.AppointmentStatus;
import com.bloodbank.bloodbankapp.exception.AppointmentSlotException;
import com.bloodbank.bloodbankapp.exception.CancelationFailedException;
import com.bloodbank.bloodbankapp.exception.NotFoundException;
import com.bloodbank.bloodbankapp.exception.ScheduleFailedException;
import com.bloodbank.bloodbankapp.mapper.AppointmentCalendarItemMapper;
import com.bloodbank.bloodbankapp.mapper.AppointmentMapper;
import com.bloodbank.bloodbankapp.model.Appointment;
import com.bloodbank.bloodbankapp.model.AppointmentSlot;
import com.bloodbank.bloodbankapp.model.Survey;
import com.bloodbank.bloodbankapp.model.User;
import com.bloodbank.bloodbankapp.repository.AppointmentRepository;
import com.bloodbank.bloodbankapp.utils.MailJetMailer;
import com.bloodbank.bloodbankapp.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
import com.mailjet.client.errors.MailjetException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.bloodbank.bloodbankapp.enums.AppointmentSlotStatus.TAKEN;
import static com.bloodbank.bloodbankapp.enums.AppointmentStatus.*;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final BloodBankService bloodBankService;

    private final UserService userService;

    private final SurveyService surveyService;

    private final AppointmentMapper appointmentMapper;

    private final AppointmentSlotService appointmentSlotService;

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public void review(AppointmentReviewDto appointmentReviewDto) {
        var appointment = appointmentRepository.findById(appointmentReviewDto.getId()).orElseThrow(() -> new NotFoundException("Appointment not found"));

        if (!appointment.getAppointmentSlot().getDateRange().dateIsDuring(LocalDateTime.now()))
            throw new AppointmentSlotException("Appointment is either before or after current time");

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

    public List<AppointmentPreviewDto> getAllByUser(Long userId) {
        return appointmentRepository.findAllByUserId(userId).stream().map(appointmentMapper::appointmentToAppointmentPreviewDto).toList();
    }

    public List<Appointment> getAllByUserApp(Long id) {
        return appointmentRepository.findAllByUserId(id);
    }

    private Appointment findLatestUserAppointment(Long userId) {
        List<Appointment> appointments = getAllByUserApp(userId);
        if (appointments.isEmpty()) return null;
        Appointment latest = appointments.get(0);

        for (Appointment appointment : appointments) {
            if (appointment.getStatus()==CANCELED) continue;

            if (latest.getAppointmentSlot().getDateRange().dateIsAfter(appointment.getAppointmentSlot().getDateRange().getStart()) && (appointment.getStatus()==SCHEDULED || appointment.getStatus()==FINISHED))
                latest = appointment;
        }

        return latest;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Appointment schedule(AppointmentSlot appointmentSlot, User user) throws MailjetException {
        try {
            Survey survey = surveyService.getByUser(user.getId());
            if (survey == null) {
                throw new ScheduleFailedException("Survey is not found");
            }
            Appointment latest = findLatestUserAppointment(user.getId());
            if (getAllByUserApp(user.getId()).isEmpty() || latest == null) {
                appointmentSlot.setStatus(TAKEN);
            }

            if (latest != null && !appointmentSlot.getDateRange().dateIsAfter(latest.getAppointmentSlot().getDateRange().getEnd().plusMonths(6))) {
                appointmentSlot.setStatus(TAKEN);
            }

            if (appointmentSlot.getStatus() == AppointmentSlotStatus.FREE) {
                throw new ScheduleFailedException("Last appointment was less than 6 months ago");
            }

            Appointment app = Appointment.builder()
                                        .appointmentSlot(appointmentSlot)
                                        .user(user)
                                        .status(SCHEDULED)
                                        .build();
            MailJetMailer.SendScheduleAppointmentMail(user.getEmail());
            appointmentRepository.save(app);
            return app;
        } catch (NotFoundException e) {
            throw new ScheduleFailedException("Survey is not found");
        }
    }

    public Appointment cancel(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException("No appointment found"));

        if (appointment.getAppointmentSlot().getDateRange().dateIsAfter(LocalDateTime.now().plusDays(1))) {
            throw new CancelationFailedException("You cannot cancel an appointment less than 24h before start");
        }

        appointment.setStatus(CANCELED);
        appointment.setAppointmentSlot(null);
        userService.penalise(appointment.getUser());
        appointmentRepository.save(appointment);
        return appointment;
    }

    public List<Appointment> getByBloodBank(long id) {
        return appointmentRepository.findAllByAppointmentSlotBloodBankId(id);
    }

    public Appointment get(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException("No appointment found"));
    }

    public List<Appointment> findAllAppointmentsByStatusByUserId(AppointmentStatus status, Long userId) {
        return appointmentRepository.findAllAppointmentsByStatusByUserId(status, userId);
    }

    public List<Appointment> findAllAppointmentsByStatusByBloodBankId(AppointmentStatus status, Long bloodBankId) {
        return appointmentRepository.findAllAppointmentsByStatusByBloodBankId(status, bloodBankId);
    }

    public List<AppointmentCalendarItemDTO> findAllByBloodBank(Long id) {
        List<Appointment> appointments = appointmentRepository.findAllByAppointmentSlot_BloodBank_Id(id);
        List<AppointmentCalendarItemDTO> appointmentDtos = new ArrayList<>();
        for (Appointment a : appointments) {
            appointmentDtos.add(AppointmentCalendarItemMapper.DtoToEntity(a));
        }
        return appointmentDtos;
    }

    public Page<Appointment> findFinishedByUser(Long id, Pageable page) {
        return appointmentRepository.findAllFinishedByUser(id, page);
    }

    public void generateQRCodeForAppointment(){
        List<Appointment>  appointments= appointmentRepository.findAll();
        Appointment appointment = appointments.get(appointments.size()-1);
        try {
            QRCodeGenerator.generateQRForAppointment(appointment.getId());
        } catch (WriterException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Appointment getLastScheduledAppointment(){
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.get(appointments.size()-1);
    }

}
