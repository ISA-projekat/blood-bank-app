package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.exception.BloodBankException;
import com.bloodbank.bloodbankapp.exception.UserException;
import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.repository.BloodBankRepository;
import com.bloodbank.bloodbankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BloodBankService {

    private final BloodBankRepository bloodBankRepository;
    private final UserRepository userRepository;

    public void edit(BloodBank bloodBank) {
        if (bloodBank == null) throw new BloodBankException("Not valid blood bank passed for edit");
        var oldBloodBank = bloodBankRepository.findById(bloodBank.getId()).orElseThrow(() -> new BloodBankException("Blood bank you want to edit doesn't exist"));
        updateBloodBank(oldBloodBank, bloodBank);
        bloodBankRepository.save(oldBloodBank);
    }

    private void updateBloodBank(BloodBank oldBloodBank, BloodBank bloodBank) {
        oldBloodBank.setAddress(bloodBank.getAddress());
        oldBloodBank.setDescription(bloodBank.getDescription());
        oldBloodBank.setName(bloodBank.getName());
    }

    public BloodBank getByUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserException("The user doesn't exist"));
        return bloodBankRepository.findById(user.getBloodBankId()).orElseThrow(() -> new UserException("The user isn't a blood bank admin"));
    }
}
