package com.bloodbank.bloodbankapp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "survey")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    Long id;

    @Column(name = "userId")
    Long userId;

    @Column(name = "surveyDate")
    private LocalDateTime surveyDate;

    @Column(nullable = false)
    Boolean weightOver50kg;

    @Column(nullable = false)
    Boolean commonCold;

    @Column(nullable = false)
    Boolean skinDiseases;

    @Column(nullable = false)
    Boolean bloodPressureProblems;

    @Column(nullable = false)
    Boolean antibiotics;

    @Column(nullable = false)
    Boolean menstrualCycle;

    @Column(nullable = false)
    Boolean dentalIntervention;

    @Column(nullable = false)
    Boolean tattooPiercing;
}
