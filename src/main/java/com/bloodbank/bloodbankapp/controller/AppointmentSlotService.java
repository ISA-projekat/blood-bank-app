package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.model.AppointmentSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/appointment-slot")
@RequiredArgsConstructor
public class AppointmentSlotService {

    private final AppointmentSlotService service;

    @CrossOrigin
    @GetMapping("/{id}")
    public AppointmentSlot get(@PathVariable("id") long id) { return service.get(id); }

    @CrossOrigin
    @GetMapping()
    public List<AppointmentSlot> getAll() { return service.getAll(); }

    @CrossOrigin
    @PostMapping()
    public AppointmentSlot create(@RequestBody AppointmentSlot appointmentSlot) { return service.create(appointmentSlot); }

    @CrossOrigin
    @DeleteMapping()
    public AppointmentSlot delete(@RequestBody AppointmentSlot appointmentSlot) { return service.delete(appointmentSlot); }

}
