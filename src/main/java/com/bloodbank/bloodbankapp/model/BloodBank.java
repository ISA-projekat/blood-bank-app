package com.bloodbank.bloodbankapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodBank extends Entity{
    private String name;
    private String description;
    private double rating;
    private Address address;
    private LocalTime startTime;
    private LocalTime endTime;
}
