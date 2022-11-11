package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.model.Address;
import com.bloodbank.bloodbankapp.model.Blood;
import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.service.BloodBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

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

    @CrossOrigin
    @GetMapping
    public List<BloodBank> getAll() {
        return bloodBankService.getAll();
    }

    @CrossOrigin
    @GetMapping("/search")
    public List<BloodBank> searchBloodBanks(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String city) {
        return bloodBankService.searchBloodBanks(name, city);
    }

    // objects instead of primitives due to nullability
    @CrossOrigin
    @GetMapping("/filter")
    public List<BloodBank> filterBloodBanks(@RequestParam(required = false) Double minRating) {
        return bloodBankService.filterBloodBanks(minRating);
    }
}
