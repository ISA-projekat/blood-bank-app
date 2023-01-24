package com.bloodbank.bloodbankapp.repository;

import com.bloodbank.bloodbankapp.model.MonthlyBloodTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyBloodTransferRepository extends JpaRepository<MonthlyBloodTransfer, Long> {

}
