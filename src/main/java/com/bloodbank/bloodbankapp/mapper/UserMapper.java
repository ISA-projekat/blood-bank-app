package com.bloodbank.bloodbankapp.mapper;

import com.bloodbank.bloodbankapp.dto.RegistrationDto;
import com.bloodbank.bloodbankapp.enums.Role;
import com.bloodbank.bloodbankapp.model.Address;
import com.bloodbank.bloodbankapp.model.User;

public class UserMapper {

    public static User DtoToEntity(RegistrationDto dto){

        final var address = Address.builder()
                .street(dto.getStreet())
                .number(dto.getNumber())
                .city(dto.getCity())
                .country(dto.getCountry()).build();

        return User.builder()
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
                .occupation(dto.getOccupation())
                .jmbg(dto.getJmbg())
                .address(address)
                .gender(dto.getGender())
                .role(Role.REGISTERED)
                .active(false)
                .penalties(0)
                .bloodBankId(null)
                .firstTimeLoginCompleted(false)
                .build();
    }
}
