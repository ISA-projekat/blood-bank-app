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

    public boolean hasBloodType(String bloodType) {
        RhFactor rhFactor = bloodStockService.parseRhFactor(bloodType);
        BloodType type = bloodStockService.parseType(bloodType);

        return !bloodStockService.findAllByTypeAndRhFactor(type, rhFactor).isEmpty();
    }

    public boolean hasBloodTypeAmount(String bloodType, int amount) {
        RhFactor rhFactor = bloodStockService.parseRhFactor(bloodType);
        BloodType type = bloodStockService.parseType(bloodType);

        return !bloodStockService.findAllByTypeAndRhFactorAndAmount(type, rhFactor, amount).isEmpty();
    }
}
