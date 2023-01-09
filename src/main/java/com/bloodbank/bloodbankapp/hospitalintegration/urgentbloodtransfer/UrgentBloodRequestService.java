package com.bloodbank.bloodbankapp.hospitalintegration.urgentbloodtransfer;

import com.bloodbank.bloodbankapp.hospitalintegration.bloodinquiry.BloodInquiryService;
import grpcService.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrgentBloodRequestService {

    private final BloodInquiryService bloodInquiryService;

    public boolean hasBlood(Model.BloodType bloodType, int amount) {
        return bloodInquiryService.hasBloodTypeAmount(bloodType.name(), amount);
    }
}
