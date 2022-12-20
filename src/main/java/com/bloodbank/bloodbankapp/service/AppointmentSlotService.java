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
    private final AppointmentSlotRepository repo;

    public AppointmentSlot get(Long id) {
        return repo.findById(id).orElseThrow(() -> new AppointmentSlotException("No appointment slot with that id was found."));
    }

    public List<AppointmentSlot> getAll() {
        List<AppointmentSlot> appointmentSlots = repo.findAll();
        if(appointmentSlots.isEmpty()) throw new AppointmentSlotException("No appointment slots were found.");
        return appointmentSlots;
    }

    public AppointmentSlot createAppointmentSlot(AppointmentSlot appointmentSlot) {
        if(!DateRange.isValid(appointmentSlot.getDateRange())) throw new AppointmentSlotException("Date range is invalid");
        repo.save(appointmentSlot);
        return appointmentSlot;
    }

    public AppointmentSlot delete(AppointmentSlot appointmentSlot) {
        repo.delete(appointmentSlot);
        return appointmentSlot;
    }

}
