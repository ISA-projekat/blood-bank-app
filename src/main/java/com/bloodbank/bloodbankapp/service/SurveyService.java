package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.exception.NotFoundException;
import com.bloodbank.bloodbankapp.model.Survey;
import com.bloodbank.bloodbankapp.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public Survey getByUser(Long id){
        Survey survey = surveyRepository.findByUserId(id);
        if (survey == null) throw new NotFoundException("Survey doesnt exist for specified user");

        return survey;
    }

    public Survey add(Survey survey){
        Survey existingSurvey = surveyRepository.findByUserId(survey.getUserId());
        if (existingSurvey == null) {
            survey.setSurveyDate(LocalDateTime.now());
            surveyRepository.save(survey);
            return survey;
        }

        refreshExistingSurvey(existingSurvey, survey);
        surveyRepository.save(existingSurvey);
        return existingSurvey;
    }

    private void refreshExistingSurvey(Survey existingSurvey, Survey survey) {
        existingSurvey.setAntibiotics(survey.getAntibiotics());
        existingSurvey.setCommonCold(survey.getCommonCold());
        existingSurvey.setMenstrualCycle(survey.getMenstrualCycle());
        existingSurvey.setSkinDiseases(survey.getSkinDiseases());
        existingSurvey.setDentalIntervention(survey.getDentalIntervention());
        existingSurvey.setTattooPiercing(survey.getTattooPiercing());
        existingSurvey.setBloodPressureProblems(survey.getBloodPressureProblems());
        existingSurvey.setWeightOver50kg(survey.getWeightOver50kg());
        existingSurvey.setSurveyDate(LocalDateTime.now());
    }

    public List<Survey> getAll(){
        return surveyRepository.findAll();
    }
}
