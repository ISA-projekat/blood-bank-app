package com.bloodbank.bloodbankapp.model;

import com.bloodbank.bloodbankapp.enums.BloodType;
import com.bloodbank.bloodbankapp.enums.RhFactor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "monthly_blood_transfer")
public class MonthlyBloodTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "blood_bank_mq_name")
    private String bloodBankMQName;

    @Column(name = "amount")
    private Double amount;

    @Column(columnDefinition = "ENUM('A', 'B', 'AB', 'O')", name = "blood_type")
    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

    @Column(columnDefinition = "ENUM('PLUS', 'MINUS')", name = "rh_factor")
    @Enumerated(EnumType.STRING)
    private RhFactor rhFactor;

    @Min(1)
    @Max(31)
    @Column(name = "_day")
    private Integer day;

    @Min(1)
    @Max(12)
    @Column(name = "_month")
    private Integer month;

    @Column(name = "warned")
    private boolean warned;

}
