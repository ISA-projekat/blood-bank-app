package com.bloodbank.bloodbankapp.hospitalintegration.urgentbloodtransfer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/urgent-blood-request")
@RequiredArgsConstructor
public class UrgentBloodRequestController {

    private final UrgentBloodRequestService service;

    @CrossOrigin
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UrgentBloodResponse UrgentBloodRequest(@RequestBody UrgentBloodRequest request){
        return new UrgentBloodResponse(service.hasBlood(request.BloodType, request.Amount));
    }
}
