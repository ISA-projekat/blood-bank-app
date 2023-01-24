package com.bloodbank.bloodbankapp.mapper;

import com.bloodbank.bloodbankapp.dto.AppointmentCalendarItemDTO;
import com.bloodbank.bloodbankapp.model.Appointment;

public class AppointmentCalendarItemMapper {

    public static AppointmentCalendarItemDTO DtoToEntity(Appointment appointment) {

        return AppointmentCalendarItemDTO.builder()
                .firstName(appointment.getUser().getFirstName())
                .lastName(appointment.getUser().getLastName())
                .startDate(appointment.getAppointmentSlot().getDateRange().getStart())
                .endDate(appointment.getAppointmentSlot().getDateRange().getEnd())
                .id(appointment.getId())
                .build();

    }
}
