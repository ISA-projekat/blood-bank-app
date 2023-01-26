package com.bloodbank.bloodbankapp.hospitalintegration.monthlytransfer;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MonthlyTransfer {

    public LocalDate DateTime;
    public int APlus;
    public int AMinus;
    public int BPlus;
    public int BMinus;
    public int ABPlus;
    public int ABMinus;
    public int OPlus;
    public int OMinus;

    public MonthlyTransfer() {
    }


}
