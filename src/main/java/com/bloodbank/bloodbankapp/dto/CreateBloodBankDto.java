package com.bloodbank.bloodbankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBloodBankDto {

    @NotBlank
    String name;

    @NotBlank
    String description;

    @NotBlank
    LocalTime startTime;

    @NotBlank
    LocalTime endTime;

    @NotBlank
    String street;

    @NotBlank
    String number;

    @NotBlank
    String city;

    @NotBlank
    String country;

}
