package com.bloodbank.bloodbankapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    private LocalDateTime date;
    private int duration;
    private double price;
    private AppointmentDetails info;
}
