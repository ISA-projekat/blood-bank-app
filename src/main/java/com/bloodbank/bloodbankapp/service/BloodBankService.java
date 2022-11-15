package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.exception.BloodBankException;
import com.bloodbank.bloodbankapp.exception.UserException;
import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.repository.BloodBankRepository;
import com.bloodbank.bloodbankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BloodBankService {

    private final BloodBankRepository bloodBankRepository;
    private final UserRepository userRepository;

    public void edit(BloodBank bloodBank) {
        if (bloodBank == null) throw new BloodBankException("Invalid blood bank passed for edit");
        var oldBloodBank = bloodBankRepository.findById(bloodBank.getId()).orElseThrow(() -> new BloodBankException("Blood bank you want to edit doesn't exist"));
        updateBloodBank(oldBloodBank, bloodBank);
        bloodBankRepository.save(oldBloodBank);
    }

    private void updateBloodBank(BloodBank oldBloodBank, BloodBank bloodBank) {
        oldBloodBank.updateAddress(bloodBank.getAddress());
        oldBloodBank.setDescription(bloodBank.getDescription());
        oldBloodBank.setName(bloodBank.getName());
    }

    public BloodBank getByUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserException("The user doesn't exist"));
        return bloodBankRepository.findById(user.getBloodBankId()).orElseThrow(() -> new UserException("The user isn't a blood bank admin"));
    }

    public List<BloodBank> getAll() {
        List<BloodBank> bloodBanks = bloodBankRepository.findAll();
        if (bloodBanks.isEmpty()) throw new BloodBankException("No blood banks found");
        return bloodBanks;
    }

    public List<BloodBank> searchBloodBanks(String name, String city) {
        List<BloodBank> bloodBanks = bloodBankRepository.searchBloodBanks(name, city);
        if (bloodBanks.isEmpty()) throw new BloodBankException("No blood banks found");
        return bloodBanks;
    }

    public BloodBank get(Long id) {
        return bloodBankRepository.findById(id).orElseThrow(() -> new BloodBankException("No banks found with that id"));
    }
}
