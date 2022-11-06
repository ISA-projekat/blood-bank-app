package com.bloodbank.bloodbankapp.model;

import com.bloodbank.bloodbankapp.enums.Gender;
import com.bloodbank.bloodbankapp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String JMBG;
    private String phoneNumber;
    private String occupation;
    private boolean active;
    private int penalties;
    private Address address;
    private Role role;
    private Gender gender;
    private BloodBank bloodBank;
}
