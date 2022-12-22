package com.bloodbank.bloodbankapp.repository;


import com.bloodbank.bloodbankapp.model.AppointmentSlot;
import com.bloodbank.bloodbankapp.model.DateRange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {

    public List<AppointmentSlot> findAllByBloodBankId(Long bloodBankId);

    public Page<AppointmentSlot> findAllByBloodBankId(Long bloodBankId, Pageable page);

    @Query("SELECT app FROM AppointmentSlot app WHERE (app.bloodBank.id = :bankId) and (app.status = 'FREE')")
    public Page<AppointmentSlot> findAllFree(@Param("bankId") Long bankId, Pageable page);

    @Query("SELECT app FROM AppointmentSlot app WHERE app.dateRange.start >= :start AND app.dateRange.end <= :end")
    public Page<AppointmentSlot> getAllInDateRange(@Param("start") Date start, @Param("end") Date end, Pageable page);

}
