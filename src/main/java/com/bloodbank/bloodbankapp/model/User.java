package com.bloodbank.bloodbankapp.model;

import com.bloodbank.bloodbankapp.enums.Gender;
import com.bloodbank.bloodbankapp.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String jmbg;
    private String phoneNumber;
    private String occupation;
    private Boolean active;
    private Integer penalties;
//    private Address address;
//    private Role role;
//    private Gender gender;
    @Column(name = "blood_bank_id")
    private Long bloodBankId;
}
