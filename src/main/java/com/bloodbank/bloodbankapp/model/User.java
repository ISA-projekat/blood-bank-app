package com.bloodbank.bloodbankapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Entity{

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String JMBG;
    private String phoneNumber;
    private String occupation;
    private boolean active;
    private int penalties;
}
