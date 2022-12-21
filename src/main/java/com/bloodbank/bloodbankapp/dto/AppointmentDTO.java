package com.bloodbank.bloodbankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    @NotBlank
    long appointmentSlotId;

    @NotBlank
    long userId;

}
