package com.bloodbank.bloodbankapp.hospitalintegration.urgentbloodtransfer;

import com.bloodbank.bloodbankapp.enums.BloodType;
import com.bloodbank.bloodbankapp.enums.RhFactor;
import com.bloodbank.bloodbankapp.model.BloodStock;
import com.bloodbank.bloodbankapp.service.BloodStockService;
import grpcService.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UrgentBloodRequestService {

    private final BloodStockService bloodStockService;

    public boolean hasBlood(Model.BloodType bloodType, int amount) {
        RhFactor rhFactor = bloodStockService.parseRhFactor(bloodType.name());
        BloodType type = bloodStockService.parseType(bloodType.name());

        List<BloodStock> bloodStockList = bloodStockService.findAllByTypeAndRhFactorAndAmount(type, rhFactor, amount);

        if (!bloodStockList.isEmpty()) {
            bloodStockService.takeBloodStockAmount(bloodStockList.get(0), amount);
            return true;
        }

        return false;
    }
}
