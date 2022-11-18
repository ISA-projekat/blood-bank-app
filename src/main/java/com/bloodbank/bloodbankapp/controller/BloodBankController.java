package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.dto.CreateBloodBankDto;
import com.bloodbank.bloodbankapp.dto.SetAdministratorToBloodBankDto;
import com.bloodbank.bloodbankapp.model.Address;
import com.bloodbank.bloodbankapp.model.Blood;
import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.service.BloodBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/bloodbank")
@RequiredArgsConstructor
public class BloodBankController {

    private final BloodBankService bloodBankService;

    @CrossOrigin
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
    @GetMapping("/{id}")
    public BloodBank get(@PathVariable("id") Long id) {
        return bloodBankService.get(id);
    }

    @CrossOrigin
    @GetMapping("/search")
    public List<BloodBank> searchBloodBanks(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String city) {
        return bloodBankService.searchBloodBanks(name, city);
    }

    @CrossOrigin
    @PostMapping("/create")
    public BloodBank create(@RequestBody  CreateBloodBankDto dto){

        return bloodBankService.createBloodBank(dto);
    }

    @CrossOrigin
    @PostMapping("/setAdministrator")
    public BloodBank addAdministratorToBloodBank(@RequestBody  SetAdministratorToBloodBankDto dto){
        return bloodBankService.addAdministratorToBloodBank(dto.getBloodBankId(),dto.getAdministratorId());
    }


}
