package com.bloodbank.bloodbankapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entity {
    private int id;
    private boolean deleted;
}
