package com.bloodbank.bloodbankapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetAdministratorToBloodBankDto {

    @NotNull
    @NotBlank
    long bloodBankId;

    @NotNull
    @NotBlank
    long administratorId;
}
