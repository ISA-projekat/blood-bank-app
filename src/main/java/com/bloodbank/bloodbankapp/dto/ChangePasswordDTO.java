package com.bloodbank.bloodbankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {

    @NotBlank
    String newPassword;

    @NotBlank
    String confirmPassword;

    @NotBlank
    Long adminId;
}
