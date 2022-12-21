package com.bloodbank.bloodbankapp.repository;


import com.bloodbank.bloodbankapp.model.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {
}
