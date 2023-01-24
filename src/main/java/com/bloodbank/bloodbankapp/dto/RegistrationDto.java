package com.bloodbank.bloodbankapp.dto;

import com.bloodbank.bloodbankapp.enums.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationDto {

    @Email
    String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    String confirmPassword;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @Size(min = 13, max = 13, message = "JMBG must be 13 characters long")
    String jmbg;

    @NotBlank
    String phoneNumber;

    @NotBlank
    String occupation;

    @NotBlank
    String street;

    @NotBlank
    String number;

    @NotBlank
    String city;

    @NotBlank
    String country;

    @NotNull
    Gender gender;
}
