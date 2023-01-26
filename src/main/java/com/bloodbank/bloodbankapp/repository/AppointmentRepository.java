package com.bloodbank.bloodbankapp.repository;

import com.bloodbank.bloodbankapp.enums.AppointmentStatus;
import com.bloodbank.bloodbankapp.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByUserId(Long userId);

    List<Appointment> findAllByAppointmentSlotBloodBankId(long bloodBankId);

    @Query("SELECT app FROM Appointment app WHERE app.status = :status and app.user.id = :id")
    List<Appointment> findAllAppointmentsByStatusByUserId(@Param("status") AppointmentStatus status, @Param("id") Long id);

    @Query("SELECT app FROM Appointment app WHERE app.status = :status and app.appointmentSlot.bloodBank.id = :id")
    List<Appointment> findAllAppointmentsByStatusByBloodBankId(@Param("status") AppointmentStatus status, @Param("id") Long id);

    List<Appointment> findAllByAppointmentSlot_BloodBank_Id(Long id);

    @Query("SELECT app FROM Appointment app WHERE (app.user.id = :userId) and (app.status = 'FINISHED')")
    Page<Appointment> findAllFinishedByUser(@Param("userId") Long userId, Pageable page);

    @Query("SELECT app FROM Appointment app WHERE app.status = :status and app.appointmentSlot.id = :id")
    Appointment findByAppointmentSlotId(@Param("status") AppointmentStatus status, @Param("id") Long appointmentSlotId);
}
