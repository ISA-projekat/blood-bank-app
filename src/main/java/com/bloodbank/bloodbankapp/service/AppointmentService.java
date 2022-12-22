package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.dto.AppointmentCalendarItemDTO;
import com.bloodbank.bloodbankapp.dto.AppointmentReviewDto;
import com.bloodbank.bloodbankapp.enums.AppointmentStatus;
import com.bloodbank.bloodbankapp.exception.NotFoundException;
import com.bloodbank.bloodbankapp.mapper.AppointmentCalendarItemMapper;
import com.bloodbank.bloodbankapp.model.Appointment;
import com.bloodbank.bloodbankapp.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.bloodbank.bloodbankapp.enums.AppointmentStatus.*;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final BloodBankService bloodBankService;

    private final UserService userService;

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

    public Appointment schedule(Appointment appointment) { return appointmentRepository.save(appointment); }

    public Appointment cancel(Appointment appointment) {
        appointmentRepository.delete(appointment);
        return appointment;
    }

    public List<AppointmentCalendarItemDTO> findAllByBloodBank(Long id){
        List<Appointment> appointments = appointmentRepository.findAllByAppointmentSlot_BloodBank_Id(id);
        List<AppointmentCalendarItemDTO> appointmentDtos = new ArrayList<>();
        for(Appointment a: appointments){
            appointmentDtos.add(AppointmentCalendarItemMapper.DtoToEntity(a));
        }
        return appointmentDtos;
    }

}
