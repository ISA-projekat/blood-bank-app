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
        // Takes an urgent blood request sent from the hospital side and sends the response for whether the blood bank has or doesn't have blood

        return new UrgentBloodResponse(service.hasBlood());
    }
}
