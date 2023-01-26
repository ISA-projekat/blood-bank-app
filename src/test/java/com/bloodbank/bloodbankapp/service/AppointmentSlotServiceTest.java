package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.dto.AppointmentReviewDto;
import com.bloodbank.bloodbankapp.enums.AppointmentStatus;
import com.bloodbank.bloodbankapp.exception.AppointmentSlotException;
import com.bloodbank.bloodbankapp.model.Appointment;
import com.bloodbank.bloodbankapp.model.AppointmentSlot;
import com.bloodbank.bloodbankapp.model.DateRange;
import com.bloodbank.bloodbankapp.repository.AppointmentRepository;
import com.bloodbank.bloodbankapp.repository.AppointmentSlotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)

class AppointmentSlotServiceTest {

    @InjectMocks
    private AppointmentSlotService appointmentSlotService;

    @Mock
    private AppointmentSlotRepository appointmentSlotRepository;

    @Test
    void shouldThrowException_whenNewAppointmentSlotDateRangeOverlapsWithAnyOldAppointmentSlot() {
        var newAppointmentSlot = new AppointmentSlot();
        var dateRange = new DateRange();
        dateRange.setStart(LocalDateTime.now().plusHours(2));
        dateRange.setEnd(LocalDateTime.now().plusHours(3));
        System.out.println(dateRange);
        newAppointmentSlot.setDateRange(dateRange);

        var oldAppointmentSlot = new AppointmentSlot();
        var oldDateRange = new DateRange();
        oldDateRange.setStart(LocalDateTime.now().plusHours(1));
        oldDateRange.setEnd(LocalDateTime.now().plusHours(4));
        System.out.println(oldDateRange);
        oldAppointmentSlot.setDateRange(oldDateRange);

        given(appointmentSlotRepository.findAll()).willReturn(List.of(oldAppointmentSlot));

        assertThrows(AppointmentSlotException.class, () -> {
            appointmentSlotService.createAppointmentSlot(newAppointmentSlot);
        });
    }

    @Test
    void shouldNotThrowException_whenNewAppointmentSlotDateRangeDoesNotOverlapWithAnyOldAppointmentSlot() {
        var newAppointmentSlot = new AppointmentSlot();
        var dateRange = new DateRange();
        dateRange.setStart(LocalDateTime.now().plusHours(1));
        dateRange.setEnd(LocalDateTime.now().plusHours(2));
        System.out.println(dateRange);
        newAppointmentSlot.setDateRange(dateRange);

        var oldAppointmentSlot = new AppointmentSlot();
        var oldDateRange = new DateRange();
        oldDateRange.setStart(LocalDateTime.now().plusHours(3));
        oldDateRange.setEnd(LocalDateTime.now().plusHours(4));
        System.out.println(oldDateRange);
        oldAppointmentSlot.setDateRange(oldDateRange);

        given(appointmentSlotRepository.findAll()).willReturn(List.of(oldAppointmentSlot));

            appointmentSlotService.createAppointmentSlot(newAppointmentSlot);
    }
}