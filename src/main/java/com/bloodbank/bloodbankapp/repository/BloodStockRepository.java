package com.bloodbank.bloodbankapp.repository;

import com.bloodbank.bloodbankapp.enums.BloodType;
import com.bloodbank.bloodbankapp.enums.RhFactor;
import com.bloodbank.bloodbankapp.model.BloodStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodStockRepository extends JpaRepository<BloodStock, Long> {

    @Query("SELECT b FROM BloodStock b WHERE (b.type = :type) and (b.rhFactor = :rh)")
    public List<BloodStock> findAllByTypeAndRhFactor(@Param("type") BloodType type, @Param("rh") RhFactor rh);

    @Query("SELECT b FROM BloodStock b WHERE (b.type = :type) and (b.rhFactor = :rh) and (b.quantity >= :amount)")
    public List<BloodStock> findAllByTypeAndRhFactorAndAmount(@Param("type") BloodType type, @Param("rh") RhFactor rh, @Param("amount") int amount);
}
