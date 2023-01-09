package com.bloodbank.bloodbankapp.hospitalintegration.bloodinquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BloodInquiryService {
    public boolean hasBloodType(String bloodType) {
        return true;
    }

    public boolean hasBloodTypeAmount(String bloodType, double amount) {
        return true;
    }
}
