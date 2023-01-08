package com.bloodbank.bloodbankapp.hospitalintegration.urgentbloodtransfer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrgentBloodRequestService {
    // fill class with methods you need for the controller to work

    public boolean hasBlood() {
        return true;
    }
}
