package com.bloodbank.bloodbankapp.model;

import com.bloodbank.bloodbankapp.utils.DateRangeJSONConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "appointment_slot")
public class AppointmentSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = DateRangeJSONConverter.class)
    private DateRange dateRange;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "blood_bank_id", referencedColumnName = "id")
    private BloodBank bloodBank;
}
