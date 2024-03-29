package com.bloodbank.bloodbankapp.dto;

import com.bloodbank.bloodbankapp.model.DateRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSlotDTO {

    @NotBlank
    DateRange dateRange;

    @NotBlank
    Long bloodBankId;
}
