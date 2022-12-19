package com.bloodbank.bloodbankapp.model;

import com.bloodbank.bloodbankapp.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime scheduledDate;

    private int duration;

    @Column(columnDefinition = "ENUM('SCHEDULED', 'CANCELED', 'FINISHED', 'NOT_ALLOWED')")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_details_id", referencedColumnName = "id")
    private AppointmentDetails details;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "blood_bank_id", referencedColumnName = "id")
    private BloodBank bloodBank;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void updateDetails(String description) {
        if (details == null) {
            details = new AppointmentDetails();
        }
        this.details.setDescription(description);
    }
}
