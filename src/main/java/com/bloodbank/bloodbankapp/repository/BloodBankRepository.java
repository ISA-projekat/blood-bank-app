package com.bloodbank.bloodbankapp.repository;

import com.bloodbank.bloodbankapp.model.Address;
import com.bloodbank.bloodbankapp.model.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {

    @Query("SELECT bb FROM BloodBank bb WHERE" +
            "(:name is null or bb.name LIKE CONCAT('%', :name, '%'))" +
            " and (:city is null or bb.address.city LIKE CONCAT('%', :city, '%'))")
    List<BloodBank> searchBloodBanks(@Param("name") String name, @Param("city") String city);

    @Query("SELECT bb FROM BloodBank bb WHERE" +
            "(:rating is null or bb.rating >= :rating)")
    List<BloodBank> filterBloodBanks(@Param("rating") Double minRating);



}
