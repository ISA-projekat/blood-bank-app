package com.bloodbank.bloodbankapp.model;

import com.bloodbank.bloodbankapp.enums.BloodType;
import com.bloodbank.bloodbankapp.enums.RhFactor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blood extends Entity {
    private BloodType bloodType;
    private RhFactor rhFactor;
    private double quantity;
}
