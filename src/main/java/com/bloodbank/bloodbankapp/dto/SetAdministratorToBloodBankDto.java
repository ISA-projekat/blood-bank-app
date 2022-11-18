package com.bloodbank.bloodbankapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetAdministratorToBloodBankDto {

    @NotBlank
    long bloodBankId;

    @NotBlank
    long administratorId;
}
