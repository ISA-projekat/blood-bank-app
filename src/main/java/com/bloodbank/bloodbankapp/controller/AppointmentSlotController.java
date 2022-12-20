package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.dto.AppointmentSlotDTO;
import com.bloodbank.bloodbankapp.model.AppointmentSlot;
import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.service.AppointmentSlotService;
import com.bloodbank.bloodbankapp.service.BloodBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/appointment-slot")
@RequiredArgsConstructor
public class AppointmentSlotController {

    private final AppointmentSlotService service;
    private final BloodBankService bbService;

    @CrossOrigin
    @GetMapping("/{id}")
    public AppointmentSlot get(@PathVariable("id") long id) { return service.get(id); }

    @CrossOrigin
    @GetMapping()
    public List<AppointmentSlot> getAll() { return service.getAll(); }

    @CrossOrigin
    @PostMapping()
    public AppointmentSlot create(@RequestBody AppointmentSlotDTO appointmentSlotDto) {
        BloodBank bloodBank = bbService.get(appointmentSlotDto.getBloodBankId());
        AppointmentSlot appointmentSlot = AppointmentSlot.builder()
                .bloodBank(bloodBank).
                dateRange(appointmentSlotDto.getDateRange()).
                build();

        return service.createAppointmentSlot(appointmentSlot);
    }

    @CrossOrigin
    @DeleteMapping()
    public AppointmentSlot delete(@RequestBody AppointmentSlot appointmentSlot) { return service.delete(appointmentSlot); }

}
