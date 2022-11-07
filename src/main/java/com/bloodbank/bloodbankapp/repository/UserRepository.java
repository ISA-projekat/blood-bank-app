package com.bloodbank.bloodbankapp.repository;

import com.bloodbank.bloodbankapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
