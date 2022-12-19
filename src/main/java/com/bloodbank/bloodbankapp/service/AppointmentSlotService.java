package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.exception.AppointmentSlotException;
import com.bloodbank.bloodbankapp.model.AppointmentSlot;
import com.bloodbank.bloodbankapp.model.DateRange;
import com.bloodbank.bloodbankapp.repository.AppointmentSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentSlotService {
    private final AppointmentSlotRepository service;

    // delete
    public AppointmentSlot get(Long id) {
        return service.findById(id).orElseThrow(() -> new AppointmentSlotException("No appointment slot with that id was found."));
    }

    public List<AppointmentSlot> getAll() {
        List<AppointmentSlot> appointmentSlots = service.findAll();
        if(appointmentSlots.isEmpty()) throw new AppointmentSlotException("No appointment slots were found.");
        return appointmentSlots;
    }

    public AppointmentSlot createAppointmentSlot(AppointmentSlot appointmentSlot) {
        if(!DateRange.isValid(appointmentSlot.getRange())) throw new AppointmentSlotException("Date range is invalid");
        service.save(appointmentSlot);
        return appointmentSlot;
    }

    public AppointmentSlot delete(AppointmentSlot appointmentSlot) {
        service.delete(appointmentSlot);
        return appointmentSlot;
    }

}
