package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.dto.AppointmentSlotDTO;
import com.bloodbank.bloodbankapp.model.AppointmentSlot;
import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.model.DateRange;
import com.bloodbank.bloodbankapp.service.AppointmentSlotService;
import com.bloodbank.bloodbankapp.service.BloodBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public AppointmentSlot get(@PathVariable("id") long id) {
        return service.get(id);
    }

    @CrossOrigin
    @GetMapping()
    public List<AppointmentSlot> getAll() {
        return service.getAll();
    }

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
    @DeleteMapping("/{id}")
    public AppointmentSlot delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }

    @CrossOrigin
    @GetMapping("/available-slots")
    public Page<AppointmentSlot> availableSlots(String start, String end, Pageable page) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);
        return service.getAllInDateRange(new DateRange(startDate, endDate), page);
    }

    @CrossOrigin
    @GetMapping("/blood-bank/{id}")
    @PreAuthorize("hasRole('REGISTERED') or hasRole('BLOOD_BANK_ADMIN') or hasRole('SYS_ADMIN')")
    public List<AppointmentSlot> getAllByBloodBank(@PathVariable("id") Long id) {
        return service.getAllByBloodBank(id);
    }

    @GetMapping("/blood-bank/free/{id}")
    @PreAuthorize("hasRole('REGISTERED') or hasRole('BLOOD_BANK_ADMIN') or hasRole('SYS_ADMIN')")
    public List<AppointmentSlot> getFreeSlotsForBloodBank(@PathVariable("id") Long id) {
        return service.getFreeSlotsByBloodBank(id);
    }

    @GetMapping("/blood-bank-page")
    @PreAuthorize("hasRole('REGISTERED') or hasRole('BLOOD_BANK_ADMIN') or hasRole('SYS_ADMIN')")
    public Page<AppointmentSlot> getAllSlotsPage(Long id, Pageable page) {
        return service.getPageByBloodBank(id, page);
    }

    @GetMapping("/blood-bank-page-free")
    @PreAuthorize("hasRole('REGISTERED') or hasRole('BLOOD_BANK_ADMIN') or hasRole('SYS_ADMIN')")
    public Page<AppointmentSlot> getFreeSlotsPage(Long id, Pageable page) {
        return service.getFreePageByBloodBank(id, page);
    }

    @CrossOrigin
    @GetMapping("/page")
    public Page<AppointmentSlot> getPage(Pageable page) {
        return service.getPage(page);
    }

}
