package com.bloodbank.bloodbankapp.model;

import com.bloodbank.bloodbankapp.enums.BloodType;
import com.bloodbank.bloodbankapp.enums.RhFactor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blood_stock")
public class BloodStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "ENUM('A', 'B', 'AB', 'O')")
    @Enumerated(EnumType.STRING)
    private BloodType type;

    @Column(columnDefinition = "ENUM('PLUS', 'MINUS')")
    @Enumerated(EnumType.STRING)
    private RhFactor rhFactor;

    private Double quantity;

    @Column(name = "blood_bank_id")
    private Long bloodBankId;

}
