package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.model.Blood;
import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.service.BloodBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/bloodbank")
@RequiredArgsConstructor
public class BloodBankController {

    private final BloodBankService bloodBankService;

    @PutMapping
    public void edit(@RequestBody BloodBank bloodBank) {
        bloodBankService.edit(bloodBank);
    }

    @GetMapping("/user/{userId}")
    public BloodBank getByUser(@PathVariable("userId") Long userId) {
        return bloodBankService.getByUser(userId);
    }
}
