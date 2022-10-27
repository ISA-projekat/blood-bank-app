package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.model.Appointment;
import com.bloodbank.bloodbankapp.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }
}
