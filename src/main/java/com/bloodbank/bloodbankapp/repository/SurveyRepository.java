package com.bloodbank.bloodbankapp.repository;

import com.bloodbank.bloodbankapp.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Survey findByUserId(Long id);
}
