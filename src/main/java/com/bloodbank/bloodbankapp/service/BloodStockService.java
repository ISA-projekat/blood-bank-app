package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.enums.BloodType;
import com.bloodbank.bloodbankapp.enums.RhFactor;
import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.model.BloodStock;
import com.bloodbank.bloodbankapp.repository.BloodStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BloodStockService {
    private final BloodStockRepository repo;

    public RhFactor parseRhFactor(String bloodType) {
        String rhFactor = "Minus";
        if(!bloodType.contains(rhFactor)) { rhFactor = "Plus"; }

        return RhFactor.valueOf(rhFactor);
    }

    public BloodType parseType(String bloodType) {
        String rhFactor = "Minus";
        if(!bloodType.contains(rhFactor)) { rhFactor = "Plus"; }

        String type = bloodType.replace(rhFactor, "");

        return BloodType.valueOf(type);
    }

    public List<BloodStock> findAllByTypeAndRhFactor(BloodType type, RhFactor rh) {
        return repo.findAllByTypeAndRhFactor(type, rh);
    }

    public List<BloodStock> findAllByTypeAndRhFactorAndAmount(BloodType type, RhFactor rh, int amount) {
        return repo.findAllByTypeAndRhFactorAndAmount(type, rh, amount);
    }

    public BloodStock delete(BloodStock bloodStock) {
        repo.delete(bloodStock);
        return bloodStock;
    }

    public BloodStock updateBloodStockQuantity(BloodStock bloodStock, double newQuantity) {
        bloodStock.setQuantity(newQuantity);
        return bloodStock;
    }

    public BloodStock takeBloodStockAmount(BloodStock bloodStock, double amount) {
        return updateBloodStockQuantity(bloodStock, bloodStock.getQuantity() - amount);
    }

}
