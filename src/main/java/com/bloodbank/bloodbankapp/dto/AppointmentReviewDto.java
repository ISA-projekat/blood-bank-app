package com.bloodbank.bloodbankapp.dto;

import com.bloodbank.bloodbankapp.enums.AppointmentStatus;
import com.bloodbank.bloodbankapp.model.BloodStock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentReviewDto {

    private Long id;

    private AppointmentStatus status;

    private Long equipmentSetsUsed;

    private BloodStock bloodStock;

    private String description;
}
