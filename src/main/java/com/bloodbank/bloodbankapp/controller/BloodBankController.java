package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.dto.CreateBloodBankDto;
import com.bloodbank.bloodbankapp.dto.SetAdministratorToBloodBankDto;
import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.service.BloodBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasRole('BLOOD_BANK_ADMIN') or hasRole('SYS_ADMIN')")
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
    @PreAuthorize("hasRole('BLOOD_BANK_ADMIN') or hasRole('SYS_ADMIN')")
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
    @PreAuthorize("hasRole('SYS_ADMIN')")
    public BloodBank create(@RequestBody CreateBloodBankDto dto) {

        return bloodBankService.createBloodBank(dto);
    }

    @CrossOrigin
    @PostMapping("/setAdministrator")
    @PreAuthorize("hasRole('BLOOD_BANK_ADMIN') or hasRole('SYS_ADMIN')")
    public BloodBank addAdministratorToBloodBank(@RequestBody SetAdministratorToBloodBankDto dto) {
        return bloodBankService.addAdministratorToBloodBank(dto.getBloodBankId(), dto.getAdministratorId());
    }

    @CrossOrigin
    @GetMapping("/page")
    public Page<BloodBank> getPage(Pageable page) {
        return bloodBankService.getPage(page);
    }

    @CrossOrigin
    @GetMapping("/by-admin/{id}")
    @PreAuthorize("hasRole('BLOOD_BANK_ADMIN') or hasRole('SYS_ADMIN')")
    public Long getBloodBankIdByAdminId(@PathVariable("id") Long adminId) {
        return bloodBankService.getBloodBankIdByAdminId(adminId);
    }
}
