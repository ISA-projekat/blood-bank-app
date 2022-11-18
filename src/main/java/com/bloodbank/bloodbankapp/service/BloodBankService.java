package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.dto.CreateBloodBankDto;
import com.bloodbank.bloodbankapp.enums.Role;
import com.bloodbank.bloodbankapp.exception.BloodBankException;
import com.bloodbank.bloodbankapp.exception.UserException;
import com.bloodbank.bloodbankapp.mapper.BloodBankMapper;
import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.model.User;
import com.bloodbank.bloodbankapp.repository.BloodBankRepository;
import com.bloodbank.bloodbankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        return bloodBankRepository.searchBloodBanks(name, city);
    }

    public BloodBank get(Long id) {
        return bloodBankRepository.findById(id).orElseThrow(() -> new BloodBankException("No banks found with that id"));
    }

    public BloodBank createBloodBank(CreateBloodBankDto dto){

        if(dataIsValid(dto) == false) throw new BloodBankException("Data is incorrect1");

        BloodBank bloodBank = BloodBankMapper.DtoToEntity(dto);

        bloodBankRepository.save(bloodBank);
        return bloodBank;


    }

    private boolean dataIsValid(CreateBloodBankDto dto){

        int result = dto.getEndTime().compareTo(dto.getStartTime());
        return result > 0 ;


    }

    public BloodBank addAdministratorToBloodBank(long bloodBankId, long adminId){

        User administrator = userRepository.getById(adminId);
        BloodBank bloodBank = bloodBankRepository.getById(bloodBankId);
        if(bloodBank == null && !administratorIsValid(administrator)){
            throw new BloodBankException("Admin or hospital does not exist");
        }

        administrator.setBloodBankId(bloodBankId);
        userRepository.save(administrator);
        addAdministratorToList(bloodBank,administrator);
        return bloodBank;
    }

    private boolean administratorIsValid(User admin){

        if(admin != null && admin.getBloodBankId() == null && admin.getRole().equals(Role.BLOOD_BANK_ADMIN)) {
            return true;
        }
        return false;
    }

    private void addAdministratorToList(BloodBank bloodBank, User administrator) {

       List<User> administrators = bloodBank.getAdministrators();

       if(administrators == null){
           administrators = new ArrayList<User>();
       }

        administrators.add(administrator);

        bloodBank.setAdministrators(administrators);
        bloodBankRepository.save(bloodBank);
    }





}
