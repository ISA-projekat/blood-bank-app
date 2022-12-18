package com.bloodbank.bloodbankapp.repository;

import com.bloodbank.bloodbankapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByUserId(Long userId);
}
