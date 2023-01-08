package com.bloodbank.bloodbankapp.hospitalintegration.bloodinquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blood-inquiry")
@RequiredArgsConstructor
public class BloodInquiryController {

    public final BloodInquiryService service;

    @GetMapping("/has-blood-type/{bloodType}")
    public ResponseEntity checkIfBloodTypeExists(@RequestHeader HttpHeaders http, @PathVariable String bloodType) {
        // Used to check whether the blood bank has a specific blood type

        return ResponseEntity.ok(service.hasBloodType(bloodType));
    }

    @GetMapping("/has-amount-of/{bloodType}/{amount}")
    public ResponseEntity<Boolean> checkBloodTypeAmount(@PathVariable String bloodType, @PathVariable double amount) {
        // Used to check whether the blood bank has a specific amount of a specific blood type

        return ResponseEntity.ok(service.hasBloodTypeAmount(bloodType, amount));
    }
}
