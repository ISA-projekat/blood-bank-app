package com.bloodbank.bloodbankapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Package extends Entity{
    private LocalDateTime date;
    private Blood blood;
    private Location location;
}
