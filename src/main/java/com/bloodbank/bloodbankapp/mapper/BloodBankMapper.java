package com.bloodbank.bloodbankapp.mapper;

import com.bloodbank.bloodbankapp.dto.CreateBloodBankDto;
import com.bloodbank.bloodbankapp.model.Address;
import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class BloodBankMapper {

    public static BloodBank DtoToEntity(CreateBloodBankDto dto) {

        final var address = Address.builder()
                .street(dto.getStreet())
                .number(dto.getNumber())
                .city(dto.getCity())
                .country(dto.getCountry()).build();

        List<User> users = new ArrayList<User>();

        return BloodBank.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .address(address)
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .rating(0)
                .administrators(users)
                .build();


    }
}
