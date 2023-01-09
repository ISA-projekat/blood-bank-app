package com.bloodbank.bloodbankapp.hospitalintegration.bloodinquiry;

import com.bloodbank.bloodbankapp.enums.BloodType;
import com.bloodbank.bloodbankapp.enums.RhFactor;
import com.bloodbank.bloodbankapp.service.BloodStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BloodInquiryService {

    private final BloodStockService bloodStockService;

    private RhFactor parseRhFactor(String bloodType) {
        String rhFactor = "Minus";
        if(!bloodType.contains(rhFactor)) { rhFactor = "Plus"; }

        return RhFactor.valueOf(rhFactor);
    }

    private BloodType parseType(String bloodType) {
        String rhFactor = "Minus";
        if(!bloodType.contains(rhFactor)) { rhFactor = "Plus"; }

        String type = bloodType.replace(rhFactor, "");

        return BloodType.valueOf(type);
    }

    public boolean hasBloodType(String bloodType) {
        RhFactor rhFactor = parseRhFactor(bloodType);
        BloodType type = parseType(bloodType);

        return !bloodStockService.findAllByTypeAndRhFactor(type, rhFactor).isEmpty();
    }

    public boolean hasBloodTypeAmount(String bloodType, int amount) {
        RhFactor rhFactor = parseRhFactor(bloodType);
        BloodType type = parseType(bloodType);

        return !bloodStockService.findAllByTypeAndRhFactorAndAmount(type, rhFactor, amount).isEmpty();
    }
}
