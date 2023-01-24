package com.bloodbank.bloodbankapp.model;

import com.bloodbank.bloodbankapp.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "ENUM('SCHEDULED', 'CANCELED', 'FINISHED', 'NOT_ALLOWED')")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_details_id", referencedColumnName = "id")
    private AppointmentDetails details;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_slot_id", referencedColumnName = "id")
    private AppointmentSlot appointmentSlot;

    public void updateDetails(String description) {
        if (details==null) {
            details = new AppointmentDetails();
        }
        this.details.setDescription(description);
    }
}
