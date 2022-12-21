package com.bloodbank.bloodbankapp.dto;

import com.bloodbank.bloodbankapp.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBloodBankDto {

    @NotNull
    @NotBlank
    String name;

    @NotNull
    @NotBlank
    String description;

    @NotNull
    @NotBlank
    LocalTime startTime;

    @NotNull
    @NotBlank
    LocalTime endTime;

    @NotNull
    @NotBlank
    String street;

    @NotNull
    @NotBlank
    String number;

    @NotNull
    @NotBlank
    String city;

    @NotNull
    @NotBlank
    String country;

}
