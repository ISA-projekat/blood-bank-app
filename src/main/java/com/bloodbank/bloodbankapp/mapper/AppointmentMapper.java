package com.bloodbank.bloodbankapp.mapper;

import com.bloodbank.bloodbankapp.dto.AppointmentPreviewDto;
import com.bloodbank.bloodbankapp.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AppointmentMapper {

    @Mapping(target = "startTime", source = "appointmentSlot.dateRange.start")
    @Mapping(target = "endTime", source = "appointmentSlot.dateRange.end")
    @Mapping(target = "bloodBankId", source = "appointmentSlot.bloodBank.id")
    @Mapping(target = "bloodBankName", source = "appointmentSlot.bloodBank.name")
    AppointmentPreviewDto appointmentToAppointmentPreviewDto(Appointment appointment);
}
