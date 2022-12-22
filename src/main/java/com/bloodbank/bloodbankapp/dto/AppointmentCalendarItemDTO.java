package com.bloodbank.bloodbankapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentCalendarItemDTO {

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    LocalDateTime startDate;

    @NotBlank
    LocalDateTime endDate;



}
