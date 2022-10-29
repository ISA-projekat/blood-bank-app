package com.bloodbank.bloodbankapp.repository;

import com.bloodbank.bloodbankapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
