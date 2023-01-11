package com.bloodbank.bloodbankapp.hospitalintegration.urgentbloodtransfer;

import grpcService.Model;

public class UrgentBloodRequest {
    public Model.BloodType BloodType;
    public int Amount;

    public UrgentBloodRequest(Model.BloodType bloodType, int amount){
        this.BloodType = bloodType;
        this.Amount = amount;
    }
}
