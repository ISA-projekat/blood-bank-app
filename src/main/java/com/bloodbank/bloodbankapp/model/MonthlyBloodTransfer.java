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

    private String bloodBankMQName;

    private Double amount;

    @Column(columnDefinition = "ENUM('A', 'B', 'AB', 'O')")
    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

    @Column(columnDefinition = "ENUM('PLUS', 'MINUS')")
    @Enumerated(EnumType.STRING)
    private RhFactor rhFactor;

    @Min(1)
    @Max(31)
    private Integer day;

    @Min(1)
    @Max(12)
    private Integer month;

}
