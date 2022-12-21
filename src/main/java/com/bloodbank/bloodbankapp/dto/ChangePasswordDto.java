package com.bloodbank.bloodbankapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ChangePasswordDto {

    @NotBlank
    @NotNull
    private long id;

    @NotBlank
    @NotNull
    private String newPassword;

    @NotNull
    @NotBlank
    private String confirmNewPassword;


}
