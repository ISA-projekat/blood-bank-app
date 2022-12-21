package com.bloodbank.bloodbankapp.repository;


import com.bloodbank.bloodbankapp.model.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {

    public List<AppointmentSlot> findAllByBloodBankId(Long bloodBankId);

}
