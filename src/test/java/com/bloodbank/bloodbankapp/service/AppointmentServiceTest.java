package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.dto.AppointmentReviewDto;
import com.bloodbank.bloodbankapp.enums.AppointmentStatus;
import com.bloodbank.bloodbankapp.exception.AppointmentSlotException;
import com.bloodbank.bloodbankapp.exception.NotFoundException;
import com.bloodbank.bloodbankapp.model.Appointment;
import com.bloodbank.bloodbankapp.model.AppointmentSlot;
import com.bloodbank.bloodbankapp.model.DateRange;
import com.bloodbank.bloodbankapp.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Test
    void shouldThrowException_whenReviewIsNotDuringAppointment() {
        var appointment = new Appointment();
        var appointmentSlot = new AppointmentSlot();
        var dateRange = new DateRange();
        dateRange.setStart(LocalDateTime.now().plusHours(1));
        dateRange.setEnd(LocalDateTime.now().plusHours(2));
        appointmentSlot.setDateRange(dateRange);
        appointment.setAppointmentSlot(appointmentSlot);
        appointment.setStatus(AppointmentStatus.FINISHED);

        var appointmentReviewDto = new AppointmentReviewDto();
        appointmentReviewDto.setId(1L);

        given(appointmentRepository.findById(appointmentReviewDto.getId())).willReturn(Optional.of(appointment));

        assertThrows(AppointmentSlotException.class, () -> {
            appointmentService.review(appointmentReviewDto);
        });
    }

    @Test
    void shouldNotThrowException_whenReviewIsDuringAppointment() {
        var appointment = new Appointment();
        var appointmentSlot = new AppointmentSlot();
        var dateRange = new DateRange();
        dateRange.setStart(LocalDateTime.now().minusHours(1));
        dateRange.setEnd(LocalDateTime.now().plusHours(1));
        appointmentSlot.setDateRange(dateRange);
        appointment.setAppointmentSlot(appointmentSlot);
        appointment.setStatus(AppointmentStatus.FINISHED);

        var appointmentReviewDto = new AppointmentReviewDto();
        appointmentReviewDto.setId(1L);

        given(appointmentRepository.findById(appointmentReviewDto.getId())).willReturn(Optional.of(appointment));

        assertThrows(NotFoundException.class, () -> {
            appointmentService.review(appointmentReviewDto);
        });
    }
}