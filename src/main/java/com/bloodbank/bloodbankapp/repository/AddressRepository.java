package com.bloodbank.bloodbankapp.repository;

import com.bloodbank.bloodbankapp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
