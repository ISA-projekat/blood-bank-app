package com.bloodbank.bloodbankapp.repository;

import com.bloodbank.bloodbankapp.enums.Role;
import com.bloodbank.bloodbankapp.model.BloodBank;
import com.bloodbank.bloodbankapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(   "SELECT u FROM User u WHERE"
            + "(:firstName is null or u.firstName LIKE CONCAT('%', :firstName, '%'))"
            + " and (:lastName is null or u.lastName LIKE CONCAT('%', :lastName, '%'))")
    List<User> search(@Param("firstName") String firstName, @Param("lastName") String lastName);

    List<User> findByRole(Role role);

    List<User> findByBloodBankId(Long bloodBankId);
}
