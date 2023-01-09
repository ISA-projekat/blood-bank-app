package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.enums.BloodType;
import com.bloodbank.bloodbankapp.enums.RhFactor;
import com.bloodbank.bloodbankapp.model.BloodStock;
import com.bloodbank.bloodbankapp.repository.BloodStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BloodStockService {
    private final BloodStockRepository repo;

    public List<BloodStock> findAllByTypeAndRhFactor(BloodType type, RhFactor rh) {
        return repo.findAllByTypeAndRhFactor(type, rh);
    }

    public List<BloodStock> findAllByTypeAndRhFactorAndAmount(BloodType type, RhFactor rh, int amount) {
        return repo.findAllByTypeAndRhFactorAndAmount(type, rh, amount);
    }

}
